package me.java.library.io.core.codec;

import io.netty.channel.ChannelHandlerContext;
import me.java.library.io.base.Cmd;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  SimpleCmdResolver
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public interface SimpleCmdResolver {

    /**
     * 读取ByteBuffer数据转换成Imsg
     *
     * @param ctx 上下文
     * @param buf ByteBuffer
     * @return Cmd
     */
    List<Cmd> bufToCmd(ChannelHandlerContext ctx, ByteBuffer buf);

    /**
     * Cmd 转换成 ByteBuffer
     *
     * @param cmd Cmd
     * @return ByteBuffer
     */
    ByteBuffer cmdToBuf(Cmd cmd);
}
