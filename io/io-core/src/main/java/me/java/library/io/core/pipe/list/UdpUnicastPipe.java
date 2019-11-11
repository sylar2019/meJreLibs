package me.java.library.io.core.pipe.list;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import me.java.library.io.core.bus.AbstractSocketBus;
import me.java.library.io.core.bus.list.UdpUnicastBus;
import me.java.library.io.core.codec.list.UdpCodec;
import me.java.library.io.core.pipe.AbstractPipe;

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
public class UdpUnicastPipe extends AbstractPipe<UdpUnicastBus, UdpCodec> {

    public UdpUnicastPipe(UdpUnicastBus bus, UdpCodec codec) {
        super(bus, codec);
    }

    @Override
    protected void onStart() {
        super.onStart();

        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(getChannelInitializer());

            ChannelFuture future = bind(b, bus.getHost(AbstractSocketBus.anyHost), bus.getPort());
            isRunning = true;
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }


}
