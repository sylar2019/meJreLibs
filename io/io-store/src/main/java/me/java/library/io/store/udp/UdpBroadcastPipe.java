package me.java.library.io.store.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import me.java.library.io.common.codec.SimpleCmdResolver;
import me.java.library.io.common.pipe.BasePipe;

/**
 * File Name             :  UdpBroadcastPipe
 *
 * @author :  sylar
 * Create :  2019-10-05
 * Description           :  UDP广播
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class UdpBroadcastPipe extends BasePipe<UdpBroadcastBus, UdpCodec> {

    /**
     * 快速创建 UdpBroadcastPipe
     *
     * @param port        本地端口
     * @param cmdResolver 指令编解码器
     * @return
     */
    public static UdpBroadcastPipe express(int port,
                                           SimpleCmdResolver cmdResolver) {
        UdpBroadcastBus bus = new UdpBroadcastBus();
        bus.setUrl(String.format("udp://localhost:%d", port));

        UdpCodec codec = new UdpCodec(cmdResolver);
        return new UdpBroadcastPipe(bus, codec);
    }

    public UdpBroadcastPipe(UdpBroadcastBus bus, UdpCodec codec) {
        super(bus, codec);
    }

    @Override
    protected ChannelFuture onStartByNetty() throws Exception {
        masterLoop = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(masterLoop)
                .channel(NioDatagramChannel.class)
                .handler(getChannelInitializer())
                .option(ChannelOption.SO_BROADCAST, true);

        return bind(bootstrap, null, bus.getPort());
    }

}
