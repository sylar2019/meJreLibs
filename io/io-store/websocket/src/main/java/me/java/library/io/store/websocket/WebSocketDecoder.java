package me.java.library.io.store.websocket;

import com.google.common.base.Preconditions;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
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
    private final WebSocketCmdResolver webSocketCmdResolver;

    public WebSocketDecoder(WebSocketCmdResolver webSocketCmdResolver) {
        Preconditions.checkNotNull(webSocketCmdResolver);
        this.webSocketCmdResolver = webSocketCmdResolver;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        WebSocketCmd cmd = webSocketCmdResolver.frameToCmd(ctx, msg);
        if (cmd != null) {
            out.add(cmd);
        }
    }
}
