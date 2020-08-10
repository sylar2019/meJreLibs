package me.java.library.io.base.pipe;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FutureCallback;
import me.java.library.common.service.ConcurrentService;
import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.cmd.CmdUtils;
import me.java.library.io.base.cmd.Host;
import me.java.library.io.base.cmd.Terminal;
import me.java.library.io.base.event.*;
import me.java.library.utils.base.guava.AsyncEventUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  AbstractPipe
 *
 * @author :  sylar
 * Create                :  2020/7/15
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public abstract class BasePipe implements Pipe {
    protected final static int DAEMON_PERIOD_SECONDS = 5;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Host host;
    protected PipeWatcher watcher;
    protected boolean isRunning;
    protected boolean isDaemon;
    protected boolean isEventEnabled;

    public BasePipe() {
        this(Host.LOCAL);
    }

    public BasePipe(Host host) {
        this.host = host;

        onHostStateChanged(true);
        registHook();
    }

    /**
     * 建造通讯链路
     * 通讯链路的建立有同步与异步两种方式
     * 同步方式下，应以顺序代码的方式调用 onPipeRunningChanged(true)
     * 异步方式下，应在相应的回调方法内调用 onPipeRunningChanged(true)
     *
     * @return
     * @throws Exception
     */
    protected abstract boolean onStart() throws Exception;

    /**
     * 拆除通讯链路
     *
     * @throws Exception
     */
    protected abstract boolean onStop() throws Exception;

    /**
     * 异步发送
     *
     * @param request
     * @throws Exception
     */
    protected abstract boolean onSend(Cmd request) throws Exception;

    /**
     * 同步发送
     *
     * @param request
     * @param timeout
     * @param unit
     * @return
     * @throws Exception
     */
    protected abstract Cmd onSyncSend(Cmd request, long timeout, TimeUnit unit) throws Exception;

    @Override
    public void start() {
        if (isRunning) {
            return;
        }

        checkOnStart();
        ConcurrentService.getInstance().postCallable(this::onStart, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean result) {
                Preconditions.checkNotNull(result);
                onPipeRunningChanged(result);
            }

            @Override
            public void onFailure(Throwable t) {
                onException(t);
                if (isDaemon) {
                    ConcurrentService.getInstance().schedule(() -> onStart(), getDaemonSeconds(), TimeUnit.SECONDS);
                }
            }
        });
    }

    @Override
    public void stop() {
        if (!isRunning) {
            return;
        }

        checkOnStop();
        ConcurrentService.getInstance().postCallable(this::onStop, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean result) {
                onPipeRunningChanged(false);
            }

            @Override
            public void onFailure(Throwable t) {
                onException(t);
            }
        });
    }

    @Override
    public Cmd syncSend(Cmd request, long timeout, TimeUnit unit, int tryTimes) throws Exception {
        Preconditions.checkNotNull(request);
        Preconditions.checkState(timeout > 0);
        Preconditions.checkState(tryTimes >= 0);

        Cmd response = null;
        Exception exception = null;
        for (int i = 0; i <= tryTimes; i++) {
            exception = null;

            try {
                response = syncSend(request, timeout, unit);
                if (response != null) {
                    break;
                }
            } catch (Exception e) {
                System.err.println("syncSend 重试次数:" + (i + 1));
                exception = e;
            }
        }

        if (exception != null) {
            throw exception;
        }
        return response;
    }

    @Override
    public Cmd syncSend(Cmd request, long timeout, TimeUnit unit) throws Exception {
        checkOnSend(request);
        return onSyncSend(request, timeout, unit);
    }

    @Override
    public Cmd syncSend(Cmd request) throws Exception {
        return syncSend(request, 1, TimeUnit.SECONDS);
    }

    @Override
    public void send(Cmd request) {
        try {
            checkOnSend(request);
            onSend(request);
        } catch (Exception e) {
            onException(e);
        }
    }

    protected void checkOnStart() {

    }

    protected void checkOnStop() {

    }

    protected void checkOnSend(Cmd request) {
        Preconditions.checkState(isRunning, "pipe is not running");
        Preconditions.checkState(CmdUtils.isValidCmd(request), "invalid request cmd");
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public Host getHost() {
        return host;
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
    public void setDaemon(boolean enabled) {
        isDaemon = enabled;
    }

    @Override
    public void dispose() {
        stop();
    }

    protected void registHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            onHostStateChanged(false);
        }));
    }

    protected void onHostStateChanged(boolean isRunning) {
        postEvent(new HostStateChangedEvent(host, isRunning));
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onHostStateChanged(host, isRunning));
        }
    }

    protected void onPipeRunningChanged(boolean isRunning) {
        if (this.isRunning == isRunning) {
            return;
        }

        this.isRunning = isRunning;
        postEvent(new PipeRunningChangedEvent(this, isRunning));
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onPipeRunningChanged(BasePipe.this, isRunning));
        }
    }

    protected void onConnectionChanged(Terminal terminal, boolean isConnected) {
        postEvent(new ConnectionChangedEvent(this, new ConnectionChangedEvent.Connection(terminal, isConnected)));
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onConnectionChanged(BasePipe.this, terminal, isConnected));
        }
    }

    protected void onReceived(Cmd cmd) {
        postEvent(new CmdReceivedEvent(this, cmd));
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onReceived(BasePipe.this, cmd));
        }
    }

    protected void onException(Throwable t) {
        logger.warn(t.getMessage(), t);
        postEvent(new PipeExceptionEvent(this, t));
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onException(BasePipe.this, t));
        }
    }

    protected void postEvent(Object event) {
        if (isEventEnabled) {
            AsyncEventUtils.postEvent(event);
        }
    }

    protected int getDaemonSeconds() {
        return DAEMON_PERIOD_SECONDS;
    }
}
