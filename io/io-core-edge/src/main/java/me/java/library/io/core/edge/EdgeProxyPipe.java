package me.java.library.io.core.edge;

import com.google.common.collect.Maps;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.SettableFuture;
import me.java.library.common.Callback;
import me.java.library.io.base.Cmd;
import me.java.library.io.base.Host;
import me.java.library.io.base.Terminal;
import me.java.library.io.core.edge.event.ConnectionChangedEvent;
import me.java.library.io.core.edge.event.HostStateChangedEvent;
import me.java.library.io.core.edge.event.InboundCmdEvent;
import me.java.library.io.core.pipe.Pipe;
import me.java.library.io.core.pipe.PipeWatcher;
import me.java.library.utils.event.guava.AsyncEventBus;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
    protected AsyncEventBus eventBus = AsyncEventBus.getInstance();
//    protected ExecutorService executor = new ThreadPoolExecutor(
//            Runtime.getRuntime().availableProcessors() * 2,
//            16,
//            10L,
//            TimeUnit.SECONDS,
//            new LinkedBlockingQueue<>(),
//            new ThreadFactoryBuilder().setNameFormat("sync-send-pool-%d").build());
//
//    ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));


    public EdgeProxyPipe(Pipe pipe) {
        this.pipe = pipe;
        eventBus.regist(this);
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


    Map<String, SyncBean> syncMap = Maps.newConcurrentMap();

    class SyncBean {
        final Cmd req;
        final SettableFuture<Cmd> future;

        public SyncBean(Cmd req) {
            this.req = req;
            this.future = SettableFuture.create();
        }

        public Cmd getReq() {
            return req;
        }

        public SettableFuture<Cmd> getFuture() {
            return future;
        }
    }

    public void syncSend(Cmd cmd, int timeoutSeconds, int tryTimes, Callback<Cmd> callback) {

        SyncBean bean = new SyncBean(cmd);
        syncMap.put(cmd.getId(), bean);

        Cmd res = null;
        for (int i = 0; i < tryTimes; i++) {
            send(cmd);
            try {
                res = bean.getFuture().get(timeoutSeconds, TimeUnit.SECONDS);
                if (res != null) {
                    break;
                }
            } catch (Exception e) {
            }
        }

        if (res != null) {
            callback.onSuccess(res);
        } else {
            callback.onFailure(new TimeoutException("timeout. cmd:" + cmd));
        }

    }

    protected boolean isSyncResponse(Cmd repCmd) {
        Cmd reqCmd = getMatchReqCmd(repCmd);
        if (reqCmd != null) {
            SyncBean bean = syncMap.remove(reqCmd.getId());
            bean.getFuture().set(repCmd);
            return true;
        }

        return false;
    }

    protected Cmd getMatchReqCmd(Cmd repCmd) {
        return null;
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

    PipeWatcher internalWatcher = new PipeWatcher() {
        @Override
        public void onReceived(Pipe pipe, Cmd cmd) {
            if (!isSyncResponse(cmd)) {
                eventBus.postEvent(new InboundCmdEvent(pipe, cmd));
            }
        }

        @Override
        public void onConnectionChanged(Pipe pipe, Terminal terminal, boolean isConnected) {
            eventBus.postEvent(new ConnectionChangedEvent(pipe, new ConnectionChangedEvent.Connection(terminal, isConnected)));
        }

        @Override
        public void onHostStateChanged(Pipe pipe, boolean isRunning) {
            eventBus.postEvent(new HostStateChangedEvent(pipe.getHost(), isRunning));
        }
    };
}
