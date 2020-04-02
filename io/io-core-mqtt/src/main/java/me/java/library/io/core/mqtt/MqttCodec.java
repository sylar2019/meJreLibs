package me.java.library.io.core.mqtt;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import me.java.library.io.core.codec.AbstractSimpleCodec;
import me.java.library.io.core.codec.Codec;
import me.java.library.io.core.codec.InboundCmdHandler;
import me.java.library.io.core.codec.SimpleCmdResolver;

import java.util.LinkedHashMap;

/**
 * File Name             :  MqttCodec
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
public class MqttCodec extends AbstractSimpleCodec {

    public static final String MQTT_ENCODER_HANDLER_NAME = "mqttEncoderHandler";
    public static final String MQTT_DECODER_HANDLER_NAME = "mqttDecoderHandler";

    public static final String M2M_ENCODER_HANDLER_NAME = "m2mEncoderHandler";
    public static final String M2M_DECODER_HANDLER_NAME = "m2mDecoderHandler";


    public MqttCodec(SimpleCmdResolver simpleCmdResolver) {
        super(simpleCmdResolver);
    }


    @Override
    protected void putHandlers(LinkedHashMap<String, ChannelHandler> handlers) {
        super.putHandlers(handlers);

        //in
        handlers.put(MQTT_DECODER_HANDLER_NAME, new MqttDecoder());
        handlers.put(M2M_DECODER_HANDLER_NAME, mqttMsgToDeviceMsgDecoder);
        handlers.put(Codec.HANDLER_NAME_INBOUND_CMD, new InboundCmdHandler());

        //out
        handlers.put(MQTT_ENCODER_HANDLER_NAME, MqttEncoder.INSTANCE);
        handlers.put(M2M_ENCODER_HANDLER_NAME, deviceMsgToMqttMsgEncoder);
    }
}
