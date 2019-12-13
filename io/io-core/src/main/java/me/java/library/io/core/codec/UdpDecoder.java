package me.java.library.io.core.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.DatagramPacketDecoder;
import me.java.library.io.Cmd;
import me.java.library.io.core.pipe.PipeAssistant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Logger logger = LoggerFactory.getLogger(getClass());

    public UdpDecoder(SimpleDecoder simpleDecoder) {
        super(simpleDecoder);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
        super.decode(ctx, datagramPacket, out);

        try {
            //记录每个udp client 的地址端口信息
            for (Object obj : out) {
                if (obj instanceof Cmd) {
                    Cmd cmd = (Cmd) obj;
                    cmd.getFrom().setInetSocketAddress(datagramPacket.sender());
                    cmd.getTo().setInetSocketAddress(datagramPacket.recipient());

                    PipeAssistant.getInstance()
                            .getPipeContext(ctx.channel())
                            .getTerminalState(cmd.getFrom())
                            .setSocketAddress(datagramPacket.sender());
                }
            }
        } catch (Exception e) {
            logger.error("decode error:" + e.getMessage());
            e.printStackTrace();
            PipeAssistant.getInstance().onThrowable(ctx.channel(), e);
        }

    }
}
