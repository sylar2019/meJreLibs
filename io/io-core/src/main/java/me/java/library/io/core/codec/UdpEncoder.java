package me.java.library.io.core.codec;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import me.java.library.io.base.cmd.Cmd;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  UdpEncoder
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
public class UdpEncoder extends SimpleEncoder {
    public final static String HANDLER_NAME = UdpEncoder.class.getSimpleName();

    public UdpEncoder(SimpleCmdResolver simpleCmdResolver) {
        super(simpleCmdResolver);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Cmd cmd, List<Object> out) throws Exception {
        super.encode(ctx, cmd, out);

        InetSocketAddress address = cmd.getTo().getInetSocketAddress();
        Preconditions.checkNotNull(address, "udp 报文需要目标套接字地址");

        //build DatagramPacket
        List<Object> raw = Lists.newArrayList(out);
        out.clear();
        for (Object obj : raw) {
            if (obj instanceof ByteBuf) {
                out.add(new DatagramPacket((ByteBuf) obj, address));
            }
        }
    }
}
