package me.java.library.io.core.codec;

import com.google.common.base.Preconditions;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import me.java.library.common.service.ConcurrentService;
import me.java.library.io.Cmd;
import me.java.library.io.CmdUtils;
import me.java.library.io.Terminal;
import me.java.library.io.core.bean.ChannelCacheService;
import me.java.library.io.core.pipe.Pipe;
import me.java.library.io.core.utils.ChannelAttr;

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

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Cmd cmd) throws Exception {
        Preconditions.checkState(CmdUtils.isValidCmd(cmd), "invalid cmd");
        ChannelCacheService.getInstance().put(cmd.getFrom(), ctx.channel());
//        onConnected(ctx, cmd.getFrom());
        onCmdReceived(ctx, cmd);
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
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    //服务端应通过设置 READER_IDLE 实现断线判断
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    //一般不需设置 WRITER_IDLE
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    //客户端应通过设置 ALL_IDLE 实现断线判断
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
            ctx.close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }

    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        onDisconnected(ctx);
    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        onDisconnected(ctx);
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {
        onDisconnected(ctx);
    }

    protected void onConnected(ChannelHandlerContext ctx, Terminal terminal) {
        //当有新连接时,在本地缓存加入channel
        //TODO 上线应由 业务层pipe 自行处理， 断线可由 IdleHandler 处理
        if (ChannelCacheService.getInstance().get(terminal) != ctx.channel()) {
            onConnectionChanged(ctx.channel(), terminal, true);
        }
    }

    protected void onDisconnected(ChannelHandlerContext ctx) {
        //当连接断开时,从本地缓存里清除channel
        Terminal terminal = ChannelAttr.get(ctx.channel(), ChannelAttr.ATTR_TERMINAL);
        ChannelCacheService.getInstance().remove(terminal);
        onConnectionChanged(ctx.channel(), terminal, false);
    }

    protected void onConnectionChanged(Channel channel, Terminal terminal, boolean isConnected) {
        Pipe pipe = ChannelAttr.get(channel, ChannelAttr.ATTR_PIPE);
        if (pipe.getWatcher() != null) {
            ConcurrentService.getInstance().postRunnable(() -> pipe.getWatcher().onConnectionChanged(pipe, terminal, isConnected));
        }
    }

    protected void onCmdReceived(ChannelHandlerContext ctx, Cmd cmd) {
        Pipe pipe = ChannelAttr.get(ctx.channel(), ChannelAttr.ATTR_PIPE);
        if (pipe.getWatcher() != null) {
            ConcurrentService.getInstance().postRunnable(() -> pipe.getWatcher().onReceived(pipe, cmd));
        }
    }

}
