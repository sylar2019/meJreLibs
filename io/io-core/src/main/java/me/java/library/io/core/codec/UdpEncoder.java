package me.java.library.io.core.codec;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import me.java.library.io.base.Cmd;

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

    public UdpEncoder(SimpleCmdResolver simpleCmdResolver) {
        super(simpleCmdResolver);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Cmd cmd, List<Object> out) throws Exception {
        if (cmd == null) {
            return;
        }

        //TODO 获取udp报文的目标地址(InetSocketAddress)
        InetSocketAddress address = null;
//        String targetDeviceId = cmd.getTargetDeviceId();
//        if (Strings.isNullOrEmpty(targetDeviceId)) {
//            return;
//        }
//
//        if (!terminalCache.containsKey(targetDeviceId)) {
//            return;
//        }
//
//        InetSocketAddress address = terminalCache.get(cmd.getTargetDeviceId());
//        if (address == null) {
//            return;
//        }

        super.encode(ctx, cmd, out);

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
