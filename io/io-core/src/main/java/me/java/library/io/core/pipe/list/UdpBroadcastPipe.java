package me.java.library.io.core.pipe.list;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import me.java.library.io.core.bus.AbstractSocketBus;
import me.java.library.io.core.bus.list.UdpBroadcastBus;
import me.java.library.io.core.codec.list.UdpCodec;
import me.java.library.io.core.pipe.AbstractPipe;

/**
 * File Name             :  UdpBroadcastPipe
 *
 * @Author :  sylar
 * @Create :  2019-10-05
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
    protected void onStart() {
        super.onStart();

        group = new NioEventLoopGroup();
        try {

            String boradcastHost = bus.getHost(AbstractSocketBus.defaultBroadcastHost);
            int boradcastPort = bus.getPort();

            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(getChannelInitializer())
                    .remoteAddress(boradcastHost, boradcastPort)
                    .option(ChannelOption.SO_BROADCAST, true);

            ChannelFuture future = bind(b, AbstractSocketBus.anyHost, boradcastPort);
            isRunning = true;
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
