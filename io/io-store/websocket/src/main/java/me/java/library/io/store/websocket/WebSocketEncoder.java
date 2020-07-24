package me.java.library.io.store.websocket;

import com.google.common.base.Preconditions;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

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
public class WebSocketEncoder extends MessageToMessageEncoder<WebSocketCmd> {
    public final static String HANDLER_NAME = WebSocketEncoder.class.getSimpleName();
    protected WebSocketCmdResolver webSocketCmdResolver;

    public WebSocketEncoder(WebSocketCmdResolver webSocketCmdResolver) {
        Preconditions.checkNotNull(webSocketCmdResolver);
        this.webSocketCmdResolver = webSocketCmdResolver;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, WebSocketCmd cmd, List<Object> out) throws Exception {
        WebSocketFrame frame = webSocketCmdResolver.cmdToFrame(cmd);
        out.add(frame);
    }
}
