package me.java.library.io.core.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import me.java.library.io.base.Cmd;

import java.nio.ByteBuffer;
import java.util.List;

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

    protected SimpleCmdResolver simpleCmdResolver;

    public SimpleEncoder(SimpleCmdResolver simpleCmdResolver) {
        super();
        this.simpleCmdResolver = simpleCmdResolver;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Cmd cmd, List<Object> out) throws Exception {
        if (cmd == null || simpleCmdResolver == null) {
            return;
        }

        try {
            ByteBuffer buf = simpleCmdResolver.cmdToBuf(cmd);
            buf.flip();
            out.add(Unpooled.copiedBuffer(buf));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}