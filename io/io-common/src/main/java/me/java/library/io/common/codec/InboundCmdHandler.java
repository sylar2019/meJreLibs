package me.java.library.io.common.codec;

import com.google.common.base.Preconditions;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import me.java.library.io.common.cmd.Cmd;
import me.java.library.io.common.cmd.CmdUtils;
import me.java.library.io.common.pipe.PipeAssistant;

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

    public final static String HANDLER_NAME = InboundCmdHandler.class.getSimpleName();

    private PipeAssistant pipeAssistant = PipeAssistant.getInstance();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Cmd cmd) throws Exception {
        Preconditions.checkState(CmdUtils.isValidCmd(cmd), "invalid cmd");
        pipeAssistant.onReceived(ctx.channel(), cmd);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        pipeAssistant.onChannleInactive(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            pipeAssistant.onChannelIdle(ctx.channel(), event);
            //空闲时断开（可能是死连接），释放连接资源
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
