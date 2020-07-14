package me.java.library.io.common.codec;

import io.netty.channel.Channel;

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
    public void initPipeLine(Channel channel) throws Exception{
        super.initPipeLine(channel);

        //in
        channel.pipeline().addLast(UdpDecoder.HANDLER_NAME, new UdpDecoder(new SimpleDecoder(simpleCmdResolver)));
        channel.pipeline().addLast(InboundCmdHandler.HANDLER_NAME, new InboundCmdHandler());

        //out
        channel.pipeline().addLast(UdpEncoder.HANDLER_NAME, new UdpEncoder(simpleCmdResolver));
    }


}
