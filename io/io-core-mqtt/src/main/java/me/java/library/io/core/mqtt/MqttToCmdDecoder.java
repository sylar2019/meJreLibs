package me.java.library.io.core.mqtt;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.mqtt.MqttMessage;

import java.util.List;

/**
 * File Name             :  M2mDecoder
 *
 * @author :  sylar
 * Create                :  2019/12/21
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
public class MqttToCmdDecoder extends MessageToMessageDecoder<MqttMessage> {
    @Override
    protected void decode(ChannelHandlerContext ctx, MqttMessage msg, List<Object> out) throws Exception {

    }
}
