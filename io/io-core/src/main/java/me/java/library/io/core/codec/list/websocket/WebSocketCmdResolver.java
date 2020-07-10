package me.java.library.io.core.codec.list.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * @author :  sylar
 * @FileName :  SimpleCmdResolver
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
public interface WebSocketCmdResolver {

    WebSocketCmdResolver DEFAULT = new WebSocketCmdResolver() {
        @Override
        public WebSocketCmdNode frameToCmd(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame) {
            if (webSocketFrame == null) {
                return null;
            }
            if (webSocketFrame instanceof TextWebSocketFrame) {
                TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) webSocketFrame;
                return WebSocketCmdNode.fromText(textWebSocketFrame.text());
            } else if (webSocketFrame instanceof BinaryWebSocketFrame) {
                BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) webSocketFrame;
                return WebSocketCmdNode.fromBinary(binaryWebSocketFrame.content());
            }
            return null;
        }

        @Override
        public WebSocketFrame cmdToFrame(WebSocketCmdNode cmd) {
            switch (cmd.getWebSocketFrameType()) {
                case Text:
                    return new TextWebSocketFrame(cmd.getTextContent());
                case Binary:
                    return new BinaryWebSocketFrame(cmd.getBinaryContent());
                default:
                    return null;
            }
        }
    };

    /**
     * WebSocketFrame to WSCmd
     *
     * @param ctx
     * @param webSocketFrame
     * @return
     */
    WebSocketCmdNode frameToCmd(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame);

    /**
     * WSCmd to WebSocketFrame
     *
     * @param cmd
     * @return
     */
    WebSocketFrame cmdToFrame(WebSocketCmdNode cmd);
}
