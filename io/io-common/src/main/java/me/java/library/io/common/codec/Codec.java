package me.java.library.io.common.codec;

import io.netty.channel.ChannelHandler;
import me.java.library.common.Identifiable;

import java.util.LinkedHashMap;

/**
 * File Name             :  Codec
 *
 * @author :  sylar
 * Create :  2019-10-05
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public interface Codec extends Identifiable<String> {

    String HANDLER_NAME_IDLE_STATE = "idleStateHandler";
    String HANDLER_NAME_LOG = "logHandler";
    String HANDLER_NAME_INBOUND_CMD = "inboundCmdHandler";
    String HANDLER_NAME_UDP_ENCODER = "udpEncoderHandler";
    String HANDLER_NAME_UDP_DECODER = "udpDecoderHandler";
    String HANDLER_NAME_SIMPLE_ENCODER = "simpleEncoderHandler";
    String HANDLER_NAME_SIMPLE_DECODER = "simpleDecoderHandler";
    String HANDLER_NAME_FRAME_DECODER = "frameDecoderHandler";

    String HANDLER_ATTR_LOG_LEVEL = "logLevel";
    String HANDLER_ATTR_READ_IDLE_TIME = "readerIdleTimeSeconds";
    String HANDLER_ATTR_WRITE_IDLE_TIME = "writerIdleTimeSeconds";
    String HANDLER_ATTR_ALL_IDLE_TIME = "allIdleTimeSeconds";

    LinkedHashMap<String, ChannelHandler> getChannelHandlers();

    <V> V getHandlerAttr(String handlerKey, String attrKey, V defaultValue);
}
