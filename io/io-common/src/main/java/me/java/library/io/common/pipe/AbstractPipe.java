package me.java.library.io.common.pipe;

import com.google.common.base.Preconditions;
import me.java.library.common.service.ConcurrentService;
import me.java.library.io.common.cmd.Cmd;
import me.java.library.io.common.cmd.CmdUtils;
import me.java.library.io.common.cmd.Host;
import me.java.library.io.common.cmd.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File Name             :  BasePipe
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
public abstract class AbstractPipe implements Pipe {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Host host;
    protected PipeWatcher watcher;
    protected boolean isRunning;
    protected boolean isDaemon;

    public AbstractPipe() {
        this(Host.LOCAL);
    }

    public AbstractPipe(Host host) {
        this.host = host;

        onHostStateChanged(true);
        registHook();
    }

    protected abstract void onStart() throws Exception;

    protected abstract void onStop() throws Exception;

    protected abstract void onSend(Cmd cmd) throws Exception;

    @Override
    public void start() {
        if (isRunning) {
            return;
        }

        try {
            onStart();
        } catch (Exception e) {
            onException(e);
        }
    }

    @Override
    public void stop() {
        if (!isRunning) {
            return;
        }

        try {
            onStop();
        } catch (Exception e) {
            onException(e);
        } finally {
            onPipeRunningChanged(false);
        }
    }

    @Override
    public void send(Cmd cmd) {
        try {
            checkOnSend(cmd);
            onSend(cmd);
        } catch (Exception e) {
            onException(e);
        }
    }

    protected void checkOnSend(Cmd cmd) {
        Preconditions.checkState(isRunning, "pipe is not running");
        Preconditions.checkState(CmdUtils.isValidCmd(cmd), "invalid cmd");
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
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onHostStateChanged(host, isRunning));
        }
    }

    protected void onPipeRunningChanged(boolean isRunning) {
        if (this.isRunning == isRunning) {
            return;
        }

        this.isRunning = isRunning;
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onPipeRunningChanged(AbstractPipe.this, isRunning));
        }
    }

    protected void onConnectionChanged(Terminal terminal, boolean isConnected) {
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onConnectionChanged(AbstractPipe.this, terminal, isConnected));
        }
    }

    protected void onReceived(Cmd cmd) {
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onReceived(AbstractPipe.this, cmd));
        }
    }

    protected void onException(Throwable t) {
        logger.warn(t.getMessage(), t);
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onException(AbstractPipe.this, t));
        }
    }
}
