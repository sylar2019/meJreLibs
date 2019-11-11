package me.java.library.io.core.codec;

import com.google.common.base.Preconditions;
import io.netty.handler.codec.ByteToMessageDecoder;

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
public class AbstractSimpleStreamCodec extends AbstractSimpleCodec {

    /**
     * @param frameDecoder      协议帧解码器
     *                          参见 netty 默认提供的 FrameDecoder
     *                          DelimiterBasedFrameDecoder       基于分隔符
     *                          FixedLengthFrameDecoder          基于固定消息长度
     *                          LengthFieldBasedFrameDecoder     基于消息长度
     *                          LineBasedFrameDecoder            基于文本换行
     * @param simpleCmdResolver
     */
    public AbstractSimpleStreamCodec(ByteToMessageDecoder frameDecoder, SimpleCmdResolver simpleCmdResolver) {
        super();

        Preconditions.checkNotNull(frameDecoder);
        Preconditions.checkNotNull(simpleCmdResolver);

        //in
        handlers.put(Codec.HANDLER_NAME_FRAME_DECODER, frameDecoder);
        handlers.put(Codec.HANDLER_NAME_SIMPLE_DECODER, new SimpleDecoder(simpleCmdResolver));
        handlers.put(Codec.HANDLER_NAME_INBOUND_CMD, new InboundCmdHandler());

        //out
        handlers.put(Codec.HANDLER_NAME_SIMPLE_ENCODER, new SimpleEncoder(simpleCmdResolver));
    }

}
