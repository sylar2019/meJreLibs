package me.java.library.io.core.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.DatagramPacketDecoder;
import me.java.library.io.base.Cmd;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  UdpDecoder
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
public class UdpDecoder extends DatagramPacketDecoder {

    public UdpDecoder(SimpleDecoder simpleDecoder) {
        super(simpleDecoder);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
        super.decode(ctx, datagramPacket, out);

        //记录每个udp client 的地址端口信息
        for (Object obj : out) {
            if (obj instanceof Cmd) {
                Cmd cmd = (Cmd) obj;
                //TODO 记录udp报文的源地址(InetSocketAddress)
//                if (!Strings.isNullOrEmpty(cmd.getSourceDeviceId())) {
//                    terminalCache.put(msg.getSourceDeviceId(), datagramPacket.from());
//                }
            }
        }
    }
}
