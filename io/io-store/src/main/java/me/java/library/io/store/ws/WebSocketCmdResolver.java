package me.java.library.io.store.ws;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import me.java.library.io.common.cmd.TerminalNode;

import java.net.InetSocketAddress;

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

            WebSocketCmdNode cmd = null;
            if (webSocketFrame instanceof TextWebSocketFrame) {
                TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) webSocketFrame;
                cmd = WebSocketCmdNode.fromText(textWebSocketFrame.text());
            } else if (webSocketFrame instanceof BinaryWebSocketFrame) {
                BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) webSocketFrame;
                cmd = WebSocketCmdNode.fromBinary(binaryWebSocketFrame.content());
            }

            if (cmd != null) {
                cmd.getFrom().setInetSocketAddress((InetSocketAddress)ctx.channel().remoteAddress());
                cmd.getTo().setInetSocketAddress((InetSocketAddress)ctx.channel().localAddress());
            }

            return cmd;
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
