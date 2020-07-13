package me.java.library.io.store.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import me.java.library.io.common.pipe.AbstractPipe;

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
public class UdpBroadcastPipe extends AbstractPipe<UdpBroadcastBus, UdpCodec> {

    public UdpBroadcastPipe(UdpBroadcastBus bus, UdpCodec codec) {
        super(bus, codec);
    }

    @Override
    protected ChannelFuture onStart() throws Exception {
        group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .handler(getChannelInitializer())
                .option(ChannelOption.SO_BROADCAST, true);

        return bind(bootstrap, null, bus.getPort());
    }

}
