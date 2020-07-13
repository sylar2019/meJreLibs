package me.java.library.io.store.ws;

import io.netty.buffer.ByteBuf;
import me.java.library.io.common.cmd.CmdNode;

/**
 * File Name             :  WSCmd
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
public class WebSocketCmdNode extends CmdNode {
    private final static String ATTR_CONTENT = "websocket-content";

    private WebSocketFrameType webSocketFrameType;

    public WebSocketFrameType getWebSocketFrameType() {
        return webSocketFrameType;
    }

    public String getTextContent() {
        return getAttr(ATTR_CONTENT);
    }

    public ByteBuf getBinaryContent() {
        return getAttr(ATTR_CONTENT);
    }


    public static WebSocketCmdNode fromText(String text) {
        WebSocketCmdNode cmd = new WebSocketCmdNode();
        cmd.setAttr(ATTR_CONTENT, text);
        return cmd;
    }

    public static WebSocketCmdNode fromBinary(ByteBuf buf) {
        WebSocketCmdNode cmd = new WebSocketCmdNode();
        cmd.setAttr(ATTR_CONTENT, buf);
        return cmd;
    }


}
