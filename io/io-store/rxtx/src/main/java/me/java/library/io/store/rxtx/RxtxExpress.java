package me.java.library.io.store.rxtx;

import io.netty.handler.codec.ByteToMessageDecoder;
import me.java.library.io.common.codec.SimpleCmdResolver;
import me.java.library.utils.rxtx.RxtxParam;

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
     * @param param        串口参数
     * @param frameDecoder 帧解析器
     * @param cmdResolver  指令编解码器
     * @return
     */
    public static RxtxPipe rxtx(RxtxParam param,
                                ByteToMessageDecoder frameDecoder,
                                SimpleCmdResolver cmdResolver) {
        RxtxBus bus = new RxtxBus();
        bus.setRxtxParam(param);

        RxtxCodec codec = new RxtxCodec(cmdResolver, frameDecoder);
        return new RxtxPipe(bus, codec);
    }

}
