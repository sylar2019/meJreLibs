package me.java.library.io.core.edge;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FutureCallback;
import me.java.library.common.event.guava.GuavaAsyncEventService;
import me.java.library.io.base.Cmd;
import me.java.library.io.base.Host;
import me.java.library.io.base.Terminal;
import me.java.library.io.core.edge.event.ConnectionChangedEvent;
import me.java.library.io.core.edge.event.HostStateChangedEvent;
import me.java.library.io.core.edge.event.InboundCmdEvent;
import me.java.library.io.core.edge.sync.SyncPairity;
import me.java.library.io.core.pipe.Pipe;
import me.java.library.io.core.pipe.PipeWatcher;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  EdgeProxyPipe
 *
 * @Author :  sylar
 * @Create :  2019-10-17
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
    protected final GuavaAsyncEventService eventBus = GuavaAsyncEventService.getInstance();
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
            public void onReceived(Pipe pipe, Cmd cmd) {
                //是否同步响应
                syncPairity.hasMatched(cmd);
                eventBus.postEvent(new InboundCmdEvent(pipe, cmd));

                if (watcher != null) {
                    watcher.onReceived(pipe, cmd);
                }
            }

            @Override
            public void onConnectionChanged(Pipe pipe, Terminal terminal, boolean isConnected) {
                eventBus.postEvent(new ConnectionChangedEvent(pipe,
                        new ConnectionChangedEvent.Connection(terminal, isConnected)));

                if (watcher != null) {
                    watcher.onConnectionChanged(pipe, terminal, isConnected);
                }
            }

            @Override
            public void onHostStateChanged(Host host, boolean isRunning) {
                eventBus.postEvent(new HostStateChangedEvent(host, isRunning));

                if (watcher != null) {
                    watcher.onHostStateChanged(host, isRunning);
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
     * @param cmd
     * @param timeoutSeconds
     * @param tryTimes
     * @param callback
     */
    public void syncSend(Cmd cmd, long timeoutSeconds, int tryTimes, FutureCallback<Cmd> callback) {
        Preconditions.checkNotNull(cmd);
        Preconditions.checkNotNull(callback);
        Preconditions.checkNotNull(syncPairity, "syncPairity can not be nul");
        Preconditions.checkState(timeoutSeconds > 0);
        Preconditions.checkState(tryTimes > 0);

        syncPairity.cacheRequest(cmd);
        Cmd res = null;
        Throwable t = null;
        for (int i = 0; i < tryTimes; i++) {
            send(cmd);
            try {
                res = syncPairity.getResponse(cmd, timeoutSeconds, TimeUnit.SECONDS);
                if (res != null) {
                    break;
                }
            } catch (Exception e) {
                t = e;
            }
        }

        if (res != null) {
            callback.onSuccess(res);
        } else {
            syncPairity.cleanCache(cmd);
            callback.onFailure(t);
        }
    }

}
