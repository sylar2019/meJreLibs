package me.java.library.io.store.rxtx;

import io.netty.handler.codec.ByteToMessageDecoder;
import me.java.library.io.core.codec.SimpleCmdResolver;

/**
 * File Name             :  RxtxExpress
 *
 * @author :  sylar
 * Create                :  2020/7/22
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
public class RxtxExpress {
    /**
     * 快速创建 RxtxPipe
     *
     * @param params       串口参数
     * @param frameDecoder 帧解析器
     * @param cmdResolver  指令编解码器
     * @return
     */
    public static RxtxPipe create(RxtxParams params,
                                  ByteToMessageDecoder frameDecoder,
                                  SimpleCmdResolver cmdResolver) {
        RxtxCodec codec = new RxtxCodec(cmdResolver, frameDecoder);
        return new RxtxPipe(params, codec);
    }

}
