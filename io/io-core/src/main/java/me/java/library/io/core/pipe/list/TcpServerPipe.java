package me.java.library.io.core.pipe.list;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.java.library.io.core.bus.AbstractSocketBus;
import me.java.library.io.core.bus.list.TcpServerBus;
import me.java.library.io.core.codec.list.TcpCodec;
import me.java.library.io.core.pipe.AbstractPipe;

/**
 * File Name             :  TcpServerPipe
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
public class TcpServerPipe extends AbstractPipe<TcpServerBus, TcpCodec> {

    protected NioEventLoopGroup childGroup;

    public TcpServerPipe(TcpServerBus bus, TcpCodec codec) {
        super(bus, codec);
    }

    @Override
    protected void onStart() {
        super.onStart();

        group = new NioEventLoopGroup();
        childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(getChannelInitializer())
                    .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bind(b, bus.getHost(AbstractSocketBus.anyHost), bus.getPort());
            isRunning = true;
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
            childGroup.shutdownGracefully();
            isRunning = false;
        }
    }

    @Override
    protected void onStop() {
        childGroup.shutdownGracefully();
        super.onStop();
    }
}
