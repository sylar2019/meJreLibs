package me.java.library.io.core.codec;

import com.google.common.base.Preconditions;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.cmd.CmdUtils;
import me.java.library.io.core.pipe.ChannelKeeper;

/**
 * @author :  sylar
 * @FileName :  InboundHandler
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
@ChannelHandler.Sharable
public class InboundHandler extends SimpleChannelInboundHandler<Cmd> {
    public final static String HANDLER_NAME = InboundHandler.class.getSimpleName();

    private ChannelKeeper channelKeeper;

    public ChannelKeeper getChannelKeeper() {
        return channelKeeper;
    }

    public void setChannelKeeper(ChannelKeeper channelKeeper) {
        this.channelKeeper = channelKeeper;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        onChannelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        onChannleInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Cmd cmd) throws Exception {
        Preconditions.checkState(CmdUtils.isValidCmd(cmd), "invalid cmd");
        onReceived(ctx, cmd);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            onIdleStateEvent(ctx, event);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    void onReceived(ChannelHandlerContext ctx, Cmd cmd) {
        if (channelKeeper != null) {
            channelKeeper.onReceived(ctx, cmd);
        }
    }

    void onChannelActive(ChannelHandlerContext ctx) {
        if (channelKeeper != null) {
            channelKeeper.onChannleActive(ctx);
        }
    }

    void onChannleInactive(ChannelHandlerContext ctx) {
        if (channelKeeper != null) {
            channelKeeper.onChannleInactive(ctx);
        }
    }

    void onIdleStateEvent(ChannelHandlerContext ctx, IdleStateEvent event) {
        if (channelKeeper != null) {
            channelKeeper.onIdleStateEvent(ctx, event);
        }
    }


}
