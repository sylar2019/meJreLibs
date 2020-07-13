package me.java.library.io.common.codec;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import me.java.library.io.common.cmd.Cmd;
import me.java.library.io.common.pipe.PipeAssistant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private Logger logger = LoggerFactory.getLogger(getClass());

    public UdpEncoder(SimpleCmdResolver simpleCmdResolver) {
        super(simpleCmdResolver);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Cmd cmd, List<Object> out) throws Exception {
        super.encode(ctx, cmd, out);

        try {
            InetSocketAddress address = cmd.getTo().getInetSocketAddress();
            if (address == null) {
                address = PipeAssistant.getInstance()
                        .getPipeContext(ctx.channel())
                        .getTerminalState(cmd.getTo())
                        .getSocketAddress();
            }
            Preconditions.checkNotNull(address, "udp 报文需要目标套接字地址");

            //build DatagramPacket
            List<Object> raw = Lists.newArrayList(out);
            out.clear();
            for (Object obj : raw) {
                if (obj instanceof ByteBuf) {
                    out.add(new DatagramPacket((ByteBuf) obj, address));
                }
            }
        } catch (Exception e) {
            logger.error("encode error:" + e.getMessage());
            e.printStackTrace();
            PipeAssistant.getInstance().onThrowable(ctx.channel(), e);
        }
    }
}
