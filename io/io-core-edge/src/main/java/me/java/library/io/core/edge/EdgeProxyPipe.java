package me.java.library.io.core.edge;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import me.java.library.common.Callback;
import me.java.library.io.base.Cmd;
import me.java.library.io.base.Host;
import me.java.library.io.base.Terminal;
import me.java.library.io.core.edge.event.ConnectionChangedEvent;
import me.java.library.io.core.edge.event.HostStateChangedEvent;
import me.java.library.io.core.edge.event.InboundCmdEvent;
import me.java.library.io.core.edge.sync.SyncPairity;
import me.java.library.io.core.pipe.Pipe;
import me.java.library.io.core.pipe.PipeWatcher;
import me.java.library.utils.event.guava.AsyncEventBus;

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

    protected Pipe pipe;
    protected PipeWatcher watcher;
    protected SyncPairity syncPairity;
    protected AsyncEventBus eventBus = AsyncEventBus.getInstance();

    public EdgeProxyPipe(Pipe pipe) {
        this.pipe = pipe;
        eventBus.regist(this);

        PipeWatcher internalWatcher = new PipeWatcher() {
            @Override
            public void onReceived(Pipe pipe, Cmd cmd) {
                //是否同步响应
                syncPairity.hasMatched(cmd);
                eventBus.postEvent(new InboundCmdEvent(pipe, cmd));
            }

            @Override
            public void onConnectionChanged(Pipe pipe, Terminal terminal, boolean isConnected) {
                eventBus.postEvent(new ConnectionChangedEvent(pipe, new ConnectionChangedEvent.Connection(terminal, isConnected)));
            }

            @Override
            public void onHostStateChanged(Host host, boolean isRunning) {
                eventBus.postEvent(new HostStateChangedEvent(host, isRunning));
            }
        };

        pipe.setWatcher(internalWatcher);
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
        eventBus.unregist(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onEvent(InboundCmdEvent event) {
        if (watcher != null) {
            watcher.onReceived(event.getSource(), event.getContent());
        }
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onEvent(ConnectionChangedEvent event) {
        if (watcher != null) {
            watcher.onConnectionChanged(event.getSource(),
                    event.getContent().getTerminal(),
                    event.getContent().isConnected());
        }
    }


    @Subscribe
    @AllowConcurrentEvents
    public void onEvent(HostStateChangedEvent event) {
        if (watcher != null) {
            watcher.onHostStateChanged(event.getSource(),
                    event.getContent());
        }
    }

    public void syncSend(Cmd cmd, long timeoutSeconds, int tryTimes, Callback<Cmd> callback) {
        Preconditions.checkNotNull(cmd);
        Preconditions.checkNotNull(callback);
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
