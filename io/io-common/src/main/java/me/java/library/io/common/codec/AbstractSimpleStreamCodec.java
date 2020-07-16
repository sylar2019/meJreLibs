package me.java.library.io.common.codec;

import com.google.common.base.Preconditions;
import io.netty.channel.Channel;
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

    final static String HANDLER_NAME_FRAME_DECODER = "frameDecoderHandler";

    /**
     * 协议帧解码器
     * 参见 netty 默认提供的 FrameDecoder
     * DelimiterBasedFrameDecoder       基于分隔符
     * FixedLengthFrameDecoder          基于固定消息长度
     * LengthFieldBasedFrameDecoder     基于消息长度
     * LineBasedFrameDecoder            基于文本换行
     */
    protected ByteToMessageDecoder frameDecoder;

    public AbstractSimpleStreamCodec(SimpleCmdResolver simpleCmdResolver, ByteToMessageDecoder frameDecoder) {
        super(simpleCmdResolver);
        this.frameDecoder = frameDecoder;
    }

    @Override
    public void initPipeLine(Channel channel) throws Exception {
        super.initPipeLine(channel);
        Preconditions.checkNotNull(frameDecoder);

        //in
        channel.pipeline().addLast(HANDLER_NAME_FRAME_DECODER, frameDecoder);
        channel.pipeline().addLast(SimpleDecoder.HANDLER_NAME, new SimpleDecoder(simpleCmdResolver));
        channel.pipeline().addLast(InboundCmdHandler.HANDLER_NAME, new InboundCmdHandler());

        //out
        channel.pipeline().addLast(SimpleEncoder.HANDLER_NAME, new SimpleEncoder(simpleCmdResolver));
    }
}
