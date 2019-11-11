package me.java.library.io.core.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.java.library.io.Cmd;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  SimpleDecoder
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
public class SimpleDecoder extends MessageToMessageDecoder<ByteBuf> {


    protected SimpleCmdResolver simpleCmdResolver;

    public SimpleDecoder(SimpleCmdResolver simpleCmdResolver) {
        super();
        this.simpleCmdResolver = simpleCmdResolver;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        if (simpleCmdResolver == null) {
            return;
        }

        try {
            List<Cmd> msgList = simpleCmdResolver.bufToCmd(ctx, buf);
            if (msgList != null && !msgList.isEmpty()) {
                out.addAll(msgList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
