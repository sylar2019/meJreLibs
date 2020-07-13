package me.java.library.io.common.codec;

import com.google.common.base.Preconditions;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.ByteToMessageDecoder;

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
public class AbstractSimpleStreamCodec extends AbstractSimpleCodec {

    /**
     * 协议帧解码器
     * 参见 netty 默认提供的 FrameDecoder
     * DelimiterBasedFrameDecoder       基于分隔符
     * FixedLengthFrameDecoder          基于固定消息长度
     * LengthFieldBasedFrameDecoder     基于消息长度
     * LineBasedFrameDecoder            基于文本换行
     */
    protected Class<?> frameDecoderClass;

    public AbstractSimpleStreamCodec(SimpleCmdResolver simpleCmdResolver, Class<? extends ByteToMessageDecoder> frameDecoderClass) {
        super(simpleCmdResolver);
        Preconditions.checkNotNull(frameDecoderClass);
        this.frameDecoderClass = frameDecoderClass;
    }

    @Override
    protected void putHandlers(LinkedHashMap<String, ChannelHandler> handlers) {
        super.putHandlers(handlers);

        ByteToMessageDecoder frameDecoder = createFrameDecoder(frameDecoderClass);
        Preconditions.checkNotNull(frameDecoder);

        //in
        handlers.put(Codec.HANDLER_NAME_FRAME_DECODER, frameDecoder);
        handlers.put(Codec.HANDLER_NAME_SIMPLE_DECODER, new SimpleDecoder(simpleCmdResolver));
        handlers.put(Codec.HANDLER_NAME_INBOUND_CMD, new InboundCmdHandler());

        //out
        handlers.put(Codec.HANDLER_NAME_SIMPLE_ENCODER, new SimpleEncoder(simpleCmdResolver));
    }

    protected ByteToMessageDecoder createFrameDecoder(Class<?> clazz) {
        try {
            Object obj = clazz.newInstance();
            Preconditions.checkNotNull(obj, "创建帧解析器异常:" + clazz.getName());
            Preconditions.checkState(obj instanceof ByteToMessageDecoder, "帧解析器基类型错误:" + clazz.getName());
            return (ByteToMessageDecoder) obj;
        } catch (Exception e) {
            return null;
        }
    }

}
