package me.java.library.io.core.codec;

import com.google.common.base.Preconditions;

/**
 * File Name             : AbstractSimpleStreamCodec
 *
 * @Author :  sylar
 * @Create :  2019-10-14
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
        super();

        Preconditions.checkNotNull(simpleCmdResolver);

        //in
        handlers.put(Codec.HANDLER_NAME_UDP_DECODER, new UdpDecoder(new SimpleDecoder(simpleCmdResolver)));
        handlers.put(Codec.HANDLER_NAME_INBOUND_CMD, new InboundCmdHandler());

        //out
        handlers.put(Codec.HANDLER_NAME_UDP_ENCODER, new UdpEncoder(simpleCmdResolver));
    }

}
