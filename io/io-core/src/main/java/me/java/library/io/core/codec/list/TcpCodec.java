package me.java.library.io.core.codec.list;

import io.netty.handler.codec.ByteToMessageDecoder;
import me.java.library.io.core.codec.AbstractSimpleStreamCodec;
import me.java.library.io.core.codec.SimpleCmdResolver;

/**
 * File Name             :  TcpCodec
 *
 * @author :  sylar
 * Create :  2019-10-15
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
public class TcpCodec extends AbstractSimpleStreamCodec {
    public TcpCodec(SimpleCmdResolver simpleCmdResolver, Class<? extends ByteToMessageDecoder> frameDecoderClass) {
        super(simpleCmdResolver, frameDecoderClass);
    }
}
