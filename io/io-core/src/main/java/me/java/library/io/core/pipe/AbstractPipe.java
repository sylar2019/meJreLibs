package me.java.library.io.core.pipe;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.cmd.Terminal;
import me.java.library.io.base.pipe.BasePipe;
import me.java.library.io.base.pipe.PipeParams;
import me.java.library.io.core.codec.Codec;
import me.java.library.io.core.codec.ExceptionHandler;
import me.java.library.io.core.codec.InboundHandler;
import me.java.library.io.core.sync.SyncPairity;

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
public abstract class AbstractPipe<Params extends PipeParams, C extends Codec>
        extends BasePipe<Params> {

    protected C codec;
    protected EventLoopGroup masterLoop;
    protected SyncPairity syncPairity;
    protected PipeContext pipeContext = new PipeContextImpl(this);
    protected ChannelKeeper channelKeeper = new ChannelKeeper() {
        @Override
        public void onChannleActive(ChannelHandlerContext ctx) {
            pipeContext.activeChannel(ctx.channel());
        }

        @Override
        public void onChannleInactive(ChannelHandlerContext ctx) {
            pipeContext.getTerminalsByChannel(ctx.channel()).forEach(terminal -> {
                AbstractPipe.this.onConnectionChanged(terminal, false);
            });

            pipeContext.inactiveChannel(ctx.channel());
        }

        @Override
        public void onIdleStateEvent(ChannelHandlerContext ctx, IdleStateEvent event) {
            System.out.println("### onChannelIdle: " + event);
            //空闲时断开(可能是死连接),释放连接资源
            ctx.close();
        }

        @Override
        public void onReceived(ChannelHandlerContext ctx, Cmd cmd) {
            Terminal terminal = cmd.getFrom();
            boolean hasTerminal = pipeContext.containsTerminal(terminal);
            pipeContext.addTerminal(ctx.channel(), terminal);
            if (!hasTerminal) {
                AbstractPipe.this.onConnectionChanged(terminal, true);
            }

            AbstractPipe.this.onReceived(cmd);
        }

        @Override
        public void onException(ChannelHandlerContext ctx, Throwable cause, boolean isInbound) {
            AbstractPipe.this.onException(cause);
        }
    };
    protected ChannelInitializer<Channel> channelInitializer = new ChannelInitializer<Channel>() {
        @Override
        protected void initChannel(Channel channel) throws Exception {
            pipeContext.initChannel(channel);
            codec.initPipeLine(channel);

            InboundHandler inboundHandler = (InboundHandler) channel.pipeline().get(InboundHandler.HANDLER_NAME);
            if (inboundHandler != null) {
                inboundHandler.setChannelKeeper(channelKeeper);
            }

            ExceptionHandler exceptionHandler = (ExceptionHandler) channel.pipeline().get(ExceptionHandler.HANDLER_NAME);
            if (exceptionHandler != null) {
                exceptionHandler.setChannelKeeper(channelKeeper);
            }
        }
    };

    public AbstractPipe(Params params, C codec) {
        super(params);
        this.codec = codec;
    }

    public SyncPairity getSyncPairity() {
        return syncPairity;
    }

    public void setSyncPairity(SyncPairity syncPairity) {
        this.syncPairity = syncPairity;
    }

    @Override
    protected void checkOnStart() {
        super.checkOnStart();
        Preconditions.checkNotNull(codec, "codec is null");
    }

    @Override
    protected boolean onStop() throws Exception {
        if (masterLoop != null) {
            masterLoop.shutdownGracefully().sync();
        }
        return true;
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
            e.printStackTrace();
            throw e;
        } finally {
            syncPairity.cleanCache(request);
        }
    }

    @Override
    protected boolean onSend(Cmd request) throws Exception {
        //查找对应channel
        Channel channel = pipeContext.getChannel(request.getTo());
        Preconditions.checkNotNull(channel);
        Preconditions.checkState(channel.isOpen(), "channel is not open");
        Preconditions.checkState(channel.isActive(), "channel is not active");
        Preconditions.checkState(channel.isWritable(), "channle is not writeable");
        channel.writeAndFlush(request);
        return true;
    }

    @Override
    public void dispose() {
        super.dispose();
        pipeContext.dispose();
    }

    protected ChannelFuture bind(AbstractBootstrap<?, ?> bootstrap, String host, int port) {
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

}
