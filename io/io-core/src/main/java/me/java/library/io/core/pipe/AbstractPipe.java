package me.java.library.io.core.pipe;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.*;
import me.java.library.common.service.ConcurrentService;
import me.java.library.io.*;
import me.java.library.io.core.bus.Bus;
import me.java.library.io.core.codec.Codec;
import me.java.library.io.core.utils.ChannelAttr;

import java.util.Map;

/**
 * File Name             :  AbstractPipe
 *
 * @author :  sylar
 * Create :  2019-10-05
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
public abstract class AbstractPipe<B extends Bus, C extends Codec> implements Pipe<B, C> {

    protected Host host;
    protected B bus;
    protected C codec;

    protected EventLoopGroup group;
    protected ChannelFuture future;
    protected Channel channel;
    protected PipeWatcher watcher;
    protected boolean isRunning;
    protected PipeAssistant pipeAssistant = PipeAssistant.getInstance();

    public AbstractPipe(B bus, C codec) {
        this("default", bus, codec);
    }

    public AbstractPipe(String hostCode, B bus, C codec) {
        pipeAssistant.addPipe(this);
        this.host = new HostNode(hostCode);
        this.bus = bus;
        this.codec = codec;

        onHostStateChanged(true);
        registHook();
    }

    @Override
    public Host getHost() {
        return host;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
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
    public void start() {
        if (isRunning) {
            return;
        }

        try {
            Preconditions.checkNotNull(bus, "bus is null");
            Preconditions.checkNotNull(codec, "codec is null");

            onStart();
            isRunning = true;
            onPipeRunningChanged(isRunning);
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
            isRunning = false;
            onPipeRunningChanged(isRunning);
        } catch (Exception e) {
            onException(e);
        }
    }

    @Override
    public void restart() {
        stop();
        start();
    }

    @Override
    public void dispose() {
        stop();
        pipeAssistant.remove(this);
    }

    @Override
    public void send(Cmd cmd) {
        try {
            Preconditions.checkNotNull(cmd);
            Preconditions.checkState(CmdUtils.isValidCmd(cmd));

            //查找对应channel
            Channel channel = pipeAssistant.getChannel(this, cmd.getTo());
            Preconditions.checkNotNull(channel);
            Preconditions.checkState(channel.isActive());
            channel.writeAndFlush(cmd);
        } catch (Exception e) {
            onException(e);
        }
    }

    protected void onStart() throws Exception {
        //do nothing
    }

    protected void onStop() throws Exception {
        if (future != null && future.channel() != null) {
            future.channel().close().sync();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
    }

    protected ChannelFuture bind(AbstractBootstrap b, String host, int port) throws InterruptedException {
        ChannelFuture future;
        if (!Strings.isNullOrEmpty(host) && port > 0) {
            future = b.bind(host, port).sync();
        } else if (port > 0) {
            future = b.bind(port).sync();
        } else {
            future = b.bind().sync();
        }

        return future;
    }

    protected ChannelInitializer<Channel> getChannelInitializer() {
        return new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                AbstractPipe.this.channel = channel;
                ChannelAttr.set(channel, ChannelAttr.ATTR_PIPE, AbstractPipe.this);

                //add channelHanders from codec
                //codec.getChannelHandlers().forEach((k, v) -> channel.pipeline().addLast(k, v)); //android 不支持
                for (Map.Entry<String, ChannelHandler> entry : codec.getChannelHandlers().entrySet()) {
                    channel.pipeline().addLast(entry.getKey(), entry.getValue());
                }
            }
        };
    }

    private void registHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            onHostStateChanged(false);
        }));
    }

    protected void onHostStateChanged(boolean isRunning) {
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onHostStateChanged(AbstractPipe.this.host, isRunning));
        }
    }

    protected void onPipeRunningChanged(boolean isRunning) {
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onPipeRunningChanged(AbstractPipe.this, isRunning));
        }
    }

    protected void onConnectionChanged(Terminal terminal, boolean isConnected) {
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onConnectionChanged(AbstractPipe.this, terminal, isConnected));
        }
    }

    /**
     * @param cmd
     */
    protected void onReceived(Cmd cmd) {
        interceptReceivedCmd(cmd);
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onReceived(AbstractPipe.this, cmd));
        }
    }

    protected void onException(Throwable t) {
        if (watcher != null) {
            ConcurrentService.getInstance().postRunnable(() -> watcher.onException(AbstractPipe.this, t));
        }
    }

    /**
     * 拦截收到的指令
     *
     * @param cmd
     */
    protected void interceptReceivedCmd(Cmd cmd) {
    }

}
