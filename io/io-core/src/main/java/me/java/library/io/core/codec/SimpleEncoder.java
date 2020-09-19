package me.java.library.io.core.codec;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import me.java.library.io.base.cmd.Cmd;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;

/**
 * @author :  sylar
 * @FileName :  SimpleEncoder
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
public class SimpleEncoder extends MessageToMessageEncoder<Cmd> {
    public final static String HANDLER_NAME = SimpleEncoder.class.getSimpleName();

    protected SimpleCmdResolver simpleCmdResolver;

    public SimpleEncoder(SimpleCmdResolver simpleCmdResolver) {
        Preconditions.checkNotNull(simpleCmdResolver);
        this.simpleCmdResolver = simpleCmdResolver;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Cmd cmd, List<Object> out) throws Exception {
        //设置发送端的socketAddress
        if (cmd.getFrom().getInetSocketAddress().getPort() == 0) {
            Optional.ofNullable(ctx.channel().localAddress()).ifPresent(v -> {
                if (v instanceof InetSocketAddress) {
                    cmd.getFrom().setInetSocketAddress((InetSocketAddress) v);
                }
            });
        }

        ByteBuf buf = simpleCmdResolver.cmdToBuf(cmd);
        out.add(buf);
    }
}
