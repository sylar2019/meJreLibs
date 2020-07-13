package me.java.library.io.common.edge;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FutureCallback;
import me.java.library.io.common.cmd.Cmd;
import me.java.library.io.common.cmd.Host;
import me.java.library.io.common.cmd.Terminal;
import me.java.library.io.common.edge.event.*;
import me.java.library.io.common.edge.sync.SyncPairity;
import me.java.library.io.common.pipe.Pipe;
import me.java.library.io.common.pipe.PipeWatcher;
import me.java.library.utils.base.guava.AsyncEventUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * File Name             :  EdgeProxyPipe
 *
 * @author :  sylar
 * Create :  2019-10-17
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class EdgeProxyPipe implements Pipe {

    protected final Pipe pipe;
    protected PipeWatcher watcher;
    protected SyncPairity syncPairity;

    public EdgeProxyPipe(Pipe pipe) {
        this(pipe, null);
    }

    public EdgeProxyPipe(Pipe pipe, SyncPairity syncPairity) {
        this.pipe = pipe;
        this.syncPairity = syncPairity;

        pipe.setWatcher(new PipeWatcher() {

            @Override
            public void onHostStateChanged(Host host, boolean isRunning) {
                postEvent(new HostStateChangedEvent(host, isRunning));
                if (watcher != null) {
                    watcher.onHostStateChanged(host, isRunning);
                }
            }

            @Override
            public void onPipeRunningChanged(Pipe pipe, boolean isRunning) {
                postEvent(new PipeRunningChangedEvent(pipe, isRunning));
                if (watcher != null) {
                    watcher.onPipeRunningChanged(pipe, isRunning);
                }
            }

            @Override
            public void onConnectionChanged(Pipe pipe, Terminal terminal, boolean isConnected) {
                postEvent(new ConnectionChangedEvent(pipe,
                        new ConnectionChangedEvent.Connection(terminal, isConnected)));

                if (watcher != null) {
                    watcher.onConnectionChanged(pipe, terminal, isConnected);
                }
            }

            @Override
            public void onReceived(Pipe pipe, Cmd cmd) {
                postEvent(new InboundCmdEvent(pipe, cmd));
                if (watcher != null) {
                    watcher.onReceived(pipe, cmd);
                }

                //是否同步响应
                if (syncPairity != null) {
                    syncPairity.hasMatched(cmd);
                }
            }

            @Override
            public void onException(Pipe pipe, Throwable t) {
                postEvent(new PipeExceptionEvent(pipe, t));
                if (watcher != null) {
                    watcher.onException(pipe, t);
                }
            }
        });
    }

    @Override
    public void start() {
        pipe.start();
    }

    @Override
    public void stop() {
        pipe.stop();
    }

    @Override
    public void restart() {
        pipe.restart();
    }

    @Override
    public boolean isRunning() {
        return pipe.isRunning();
    }

    @Override
    public Host getHost() {
        return pipe.getHost();
    }

    @Override
    public void send(Cmd cmd) {
        pipe.send(cmd);
    }

    @Override
    public long getDaemonSeconds() {
        return pipe.getDaemonSeconds();
    }

    @Override
    public void setDaemonSeconds(long seconds) {
        pipe.setDaemonSeconds(seconds);
    }

    @Override
    public PipeWatcher getWatcher() {
        return watcher;
    }

    @Override
    public void setWatcher(PipeWatcher watcher) {
        this.watcher = watcher;
    }

    @Override
    public void dispose() {
        pipe.dispose();
    }

    /**
     * 设置指令配对器
     *
     * @param syncPairity
     */
    public void setSyncPairity(SyncPairity syncPairity) {
        this.syncPairity = syncPairity;
    }

    /**
     * 同步发送指令
     *
     * @param request        待发送指令
     * @param timeoutSeconds 超时时间(秒)
     * @param tryTimes       重试次数(须大于等于0)
     * @param callback       回调
     */
    public void syncSend(Cmd request, long timeoutSeconds, int tryTimes, FutureCallback<Cmd> callback) {
        try {
            Cmd response = syncSend(request, timeoutSeconds, tryTimes);
            callback.onSuccess(response);
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    synchronized public Cmd syncSend(Cmd request, long timeoutSeconds, int tryTimes) throws Exception {
        Preconditions.checkNotNull(request);
        Preconditions.checkNotNull(syncPairity, "syncPairity can not be null");
        Preconditions.checkState(timeoutSeconds > 0);
        Preconditions.checkState(tryTimes >= 0);

        Cmd response = null;
        Exception err = null;
        for (int i = 0; i <= tryTimes; i++) {
            try {
                syncPairity.cacheRequest(request);
                send(request);
                response = syncPairity.getResponse(request, timeoutSeconds, TimeUnit.SECONDS);
                if (response != null) {
                    break;
                }
            } catch (Exception e) {
                System.err.println("同步异常， i = " + i);
                e.printStackTrace();
                err = e;
            } finally {
                syncPairity.cleanCache(request);
            }
        }

        if (response != null) {
            return response;
        } else {
            if (err instanceof TimeoutException) {
                throw new TimeoutException("同步超时: \r\n" + request);
            } else {
                throw new Exception("同步异常:\r\n" + request, err);
            }
        }
    }

    protected void postEvent(Object event) {
        AsyncEventUtils.postEvent(event);
    }

}