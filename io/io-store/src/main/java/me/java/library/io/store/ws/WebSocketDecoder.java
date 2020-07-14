package me.java.library.io.store.ws;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import me.java.library.io.common.pipe.PipeAssistant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * File Name             :  WSDecoder
 *
 * @author :  sylar
 * Create                :  2020/7/9
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
@ChannelHandler.Sharable
public class WebSocketDecoder extends MessageToMessageDecoder<WebSocketFrame> {

    public final static String HANDLER_NAME = WebSocketDecoder.class.getSimpleName();
    private Logger logger = LoggerFactory.getLogger(getClass());
    private WebSocketCmdResolver webSocketCmdResolver;

    public WebSocketDecoder(WebSocketCmdResolver webSocketCmdResolver) {
        this.webSocketCmdResolver = webSocketCmdResolver;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        if (webSocketCmdResolver == null) {
            return;
        }

        try {
            WebSocketCmdNode cmd = webSocketCmdResolver.frameToCmd(ctx, msg);
            if (cmd != null) {
                out.add(cmd);
            }
        } catch (Exception e) {
            logger.error("decode error:" + e.getMessage());
            e.printStackTrace();
            PipeAssistant.getInstance().onThrowable(ctx.channel(), e);
        }
    }
}
