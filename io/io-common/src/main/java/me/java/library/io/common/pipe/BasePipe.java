package me.java.library.io.common.pipe;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.*;
import me.java.library.io.common.bus.Bus;
import me.java.library.io.common.cmd.Cmd;
import me.java.library.io.common.cmd.Host;
import me.java.library.io.common.codec.Codec;
import me.java.library.io.common.sync.SyncPairity;
import me.java.library.io.common.utils.ChannelAttr;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  BasePipe
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
public abstract class BasePipe<B extends Bus, C extends Codec> extends AbstractPipe {
    final static int DAEMON_PERIOD_SECONDS = 5;

    protected B bus;
    protected C codec;

    protected EventLoopGroup masterLoop;
    protected ChannelFuture future;
    protected Channel channel;
    protected SyncPairity syncPairity;
    protected PipeAssistant pipeAssistant = PipeAssistant.getInstance();

    public BasePipe(B bus, C codec) {
        this(Host.LOCAL, bus, codec);
    }

    public BasePipe(Host host, B bus, C codec) {
        super(host);

        this.bus = bus;
        this.codec = codec;
        pipeAssistant.addPipe(this);
    }

    protected abstract ChannelFuture onStartByNetty() throws Exception;

    public SyncPairity getSyncPairity() {
        return syncPairity;
    }

    public void setSyncPairity(SyncPairity syncPairity) {
        this.syncPairity = syncPairity;
    }

    @Override
    protected void onStart() throws Exception {
        Preconditions.checkNotNull(bus, "bus is null");
        Preconditions.checkNotNull(codec, "codec is null");
        future = onStartByNetty();

        future.addListener(new ConnectionListener());
    }

    @Override
    protected void onStop() throws Exception {
        if (future != null && future.channel() != null) {
            future.channel().close().sync();
        }
        if (masterLoop != null) {
            masterLoop.shutdownGracefully();
        }
    }

    @Override
    protected Cmd onSyncSend(Cmd request, long timeout, TimeUnit unit) throws Exception {
        SyncPairity syncPairity = getSyncPairity();
        Preconditions.checkNotNull(syncPairity, "syncPairity can not be null");

        try {
            syncPairity.cacheRequest(request);
            onSend(request);
            return syncPairity.getResponse(request, timeout, unit);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            syncPairity.cleanCache(request);
        }
    }

    @Override
    protected void onSend(Cmd request) throws Exception {
        //查找对应channel
        Channel channel = pipeAssistant.getChannel(this, request.getTo());
        Preconditions.checkNotNull(channel);
        Preconditions.checkState(channel.isOpen(), "channel is not open");
        Preconditions.checkState(channel.isActive(), "channel is not active");
        Preconditions.checkState(channel.isWritable(), "channle is not writeable");
        channel.writeAndFlush(request);
    }

    @Override
    public void dispose() {
        super.dispose();
        pipeAssistant.remove(this);
    }

    protected ChannelFuture bind(AbstractBootstrap bootstrap, String host, int port) {
        ChannelFuture future;
        if (!Strings.isNullOrEmpty(host) && port >= 0) {
            future = bootstrap.bind(host, port);
        } else if (port >= 0) {
            future = bootstrap.bind(port);
        } else {
            future = bootstrap.bind(0);
        }

        return future;
    }

    protected ChannelInitializer<Channel> getChannelInitializer() {
        return new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                BasePipe.this.channel = channel;
                ChannelAttr.set(channel, ChannelAttr.ATTR_PIPE, BasePipe.this);
                codec.initPipeLine(channel);
            }
        };
    }

    class ConnectionListener implements ChannelFutureListener {
        @Override
        public void operationComplete(final ChannelFuture future) {
            if (future.isSuccess()) {
                onPipeRunningChanged(true);
            } else if (isDaemon && getDaemonSeconds() > 0) {
                //守护进程，自动重开
                final EventLoop loop = future.channel().eventLoop();
                loop.schedule(BasePipe.this::start, getDaemonSeconds(), TimeUnit.SECONDS);
            }
        }
    }

    protected int getDaemonSeconds() {
        return DAEMON_PERIOD_SECONDS;
    }

}
