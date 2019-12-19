package me.java.library.io.core.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.java.library.io.Cmd;
import me.java.library.io.core.pipe.PipeAssistant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Logger logger = LoggerFactory.getLogger(getClass());

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
            logger.error("decode error:" + e.getMessage());
            e.printStackTrace();
            PipeAssistant.getInstance().onThrowable(ctx.channel(), e);
        }
    }
}
