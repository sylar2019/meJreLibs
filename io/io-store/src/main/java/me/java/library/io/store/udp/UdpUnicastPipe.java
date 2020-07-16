package me.java.library.io.store.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import me.java.library.io.common.codec.SimpleCmdResolver;
import me.java.library.io.common.pipe.BasePipe;

/**
 * File Name             :  UdpUnicastPipe
 *
 * @author :  sylar
 * Create :  2019-10-05
 * Description           :  UDP单播
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class UdpUnicastPipe extends BasePipe<UdpUnicastBus, UdpCodec> {

    /**
     * 快速创建 UdpUnicastPipe
     *
     * @param port        本地端口
     * @param cmdResolver 指令编解码器
     * @return
     */
    public static UdpUnicastPipe express(int port,
                                         SimpleCmdResolver cmdResolver) {
        UdpUnicastBus bus = new UdpUnicastBus();
        bus.setUrl(String.format("udp://localhost:%d", port));

        UdpCodec codec = new UdpCodec(cmdResolver);
        return new UdpUnicastPipe(bus, codec);
    }


    public UdpUnicastPipe(UdpUnicastBus bus, UdpCodec codec) {
        super(bus, codec);
    }

    @Override
    protected ChannelFuture onStartByNetty() throws Exception {
        masterLoop = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(masterLoop)
                .channel(NioDatagramChannel.class)
                .handler(getChannelInitializer());

        return bind(bootstrap, null, bus.getPort());
    }


}
