package me.java.library.io.store.ws;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import me.java.library.io.common.pipe.PipeAssistant;
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
public class WebSocketEncoder extends MessageToMessageEncoder<WebSocketCmdNode> {
    public final static String HANDLER_NAME = WebSocketEncoder.class.getSimpleName();
    protected WebSocketCmdResolver webSocketCmdResolver;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public WebSocketEncoder(WebSocketCmdResolver webSocketCmdResolver) {
        this.webSocketCmdResolver = webSocketCmdResolver;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, WebSocketCmdNode cmd, List<Object> out) throws Exception {
        if (cmd == null || webSocketCmdResolver == null) {
            return;
        }

        try {
            WebSocketFrame frame = webSocketCmdResolver.cmdToFrame(cmd);
            out.add(frame);
        } catch (Exception e) {
            logger.error("encode error:" + e.getMessage());
            e.printStackTrace();
            PipeAssistant.getInstance().onThrowable(ctx.channel(), e);
        }
    }
}
