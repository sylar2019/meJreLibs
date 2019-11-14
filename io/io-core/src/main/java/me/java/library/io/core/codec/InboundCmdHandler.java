package me.java.library.io.core.codec;

import com.google.common.base.Preconditions;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import me.java.library.io.Cmd;
import me.java.library.io.CmdUtils;
import me.java.library.io.core.pipe.PipeAssistant;

/**
 * @author :  sylar
 * @FileName :  InboundCmdHandler
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
public class InboundCmdHandler extends SimpleChannelInboundHandler<Cmd> {

    private PipeAssistant pipeAssistant = PipeAssistant.getInstance();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Cmd cmd) throws Exception {
        Preconditions.checkState(CmdUtils.isValidCmd(cmd), "invalid cmd");
        pipeAssistant.onReceived(ctx.channel(), cmd);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        /*
         * Netty 实现心跳机制与断线重连
         * https://segmentfault.com/a/1190000006931568?utm_source=tag-newest
         * */

        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            pipeAssistant.onChannelIdle(ctx.channel(), event);
            ctx.close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        pipeAssistant.onThrowable(ctx.channel(), cause);
        ctx.close();
    }
}
