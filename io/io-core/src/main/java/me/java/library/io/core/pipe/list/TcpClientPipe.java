package me.java.library.io.core.pipe.list;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.java.library.io.core.bus.list.TcpClientBus;
import me.java.library.io.core.codec.list.TcpCodec;
import me.java.library.io.core.pipe.AbstractPipe;

/**
 * File Name             :  TcpClientPipe
 *
 * @Author :  sylar
 * @Create :  2019-10-05
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class TcpClientPipe extends AbstractPipe<TcpClientBus, TcpCodec> {

    public TcpClientPipe(TcpClientBus bus, TcpCodec codec) {
        super(bus, codec);
    }

    @Override
    protected void onStart() {
        super.onStart();

        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(getChannelInitializer())
                    .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = b.connect(bus.getHost(), bus.getPort()).sync();
            isRunning = true;
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
            isRunning = false;
        }
    }
}
