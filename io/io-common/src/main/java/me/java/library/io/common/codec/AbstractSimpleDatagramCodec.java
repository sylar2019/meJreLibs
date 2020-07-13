package me.java.library.io.common.codec;

import io.netty.channel.ChannelHandler;

import java.util.LinkedHashMap;

/**
 * File Name             : AbstractSimpleStreamCodec
 *
 * @author :  sylar
 * Create :  2019-10-14
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
public abstract class AbstractSimpleDatagramCodec extends AbstractSimpleCodec {

    public AbstractSimpleDatagramCodec(SimpleCmdResolver simpleCmdResolver) {
        super(simpleCmdResolver);
    }

    @Override
    protected void putHandlers(LinkedHashMap<String, ChannelHandler> handlers) {
        super.putHandlers(handlers);

        //in
        handlers.put(Codec.HANDLER_NAME_UDP_DECODER, new UdpDecoder(new SimpleDecoder(simpleCmdResolver)));
        handlers.put(Codec.HANDLER_NAME_INBOUND_CMD, new InboundCmdHandler());

        //out
        handlers.put(Codec.HANDLER_NAME_UDP_ENCODER, new UdpEncoder(simpleCmdResolver));
    }
}
