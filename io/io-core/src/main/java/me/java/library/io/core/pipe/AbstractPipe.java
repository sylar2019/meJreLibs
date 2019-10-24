package me.java.library.io.core.pipe;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import me.java.library.common.service.ConcurrentService;
import me.java.library.io.base.Cmd;
import me.java.library.io.base.Host;
import me.java.library.io.base.HostNode;
import me.java.library.io.base.cmd.CmdUtils;
import me.java.library.io.core.bean.ChannelCacheService;
import me.java.library.io.core.bus.Bus;
import me.java.library.io.core.codec.Codec;
import me.java.library.io.core.utils.ChannelAttr;

/**
 * File Name             :  AbstractPipe
 *
 * @Author :  sylar
 * @Create :  2019-10-05
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
    protected boolean isRunning;
    protected Channel channel;
    protected PipeWatcher watcher;

    public AbstractPipe(B bus, C codec) {
        this("default", bus, codec);
    }

    public AbstractPipe(String hostCode, B bus, C codec) {
        this.host = new HostNode(hostCode);
        this.bus = bus;
        this.codec = codec;

        onHostStateChanged(true);
        registHook();
    }

    @Override
    public void dispose() {
        stop();
    }

    @Override
    public void start() {
        Preconditions.checkNotNull(bus);
        Preconditions.checkNotNull(codec);

        if (isRunning) {
            return;
        }

        onStart();
    }

    @Override
    public void stop() {
        if (!isRunning) {
            return;
        }

        onStop();
    }

    @Override
    public void restart() {
        stop();
        start();
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
    public void send(Cmd cmd) {
        Preconditions.checkNotNull(cmd);
        Preconditions.checkState(CmdUtils.isValidCmd(cmd));

        //查找对应channel
        Channel channel = ChannelCacheService.getInstance().get(cmd.getTo());
//        NettyUtils.writeData(channel, cmd);
        Preconditions.checkNotNull(channel);
        Preconditions.checkState(channel.isActive());
        channel.writeAndFlush(cmd);
    }

    protected void onStart() {
        //do nothing
    }

    protected void onStop() {
        if (group != null) {
            group.shutdownGracefully();
        }
        isRunning = false;
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
                //关联 channel 与 pipe
                ChannelAttr.set(channel, ChannelAttr.ATTR_PIPE, AbstractPipe.this);

                //add channelHanders from codec
                codec.getChannelHandlers().forEach((k, v) -> channel.pipeline().addLast(k, v));
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
}
