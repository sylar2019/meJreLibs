package me.java.library.io.core.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import me.java.library.io.Cmd;
import me.java.library.io.core.pipe.PipeAssistant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Logger logger = LoggerFactory.getLogger(getClass());

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
            ByteBuf buf = simpleCmdResolver.cmdToBuf(cmd);
            out.add(buf);
        } catch (Exception e) {
            logger.error("encode error:" + e.getMessage());
            e.printStackTrace();
            PipeAssistant.getInstance().onThrowable(ctx.channel(), e);
        }
    }
}
