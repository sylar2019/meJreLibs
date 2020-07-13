package me.java.library.io.store.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.java.library.io.common.pipe.AbstractPipe;

/**
 * File Name             :  TcpServerPipe
 *
 * @author :  sylar
 * Create :  2019-10-05
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
public class WebSocketServerPipe extends AbstractPipe<WebSocketServerBus, WebSocketServerCodec> {

    protected NioEventLoopGroup childGroup;

    public WebSocketServerPipe(WebSocketServerBus bus, WebSocketServerCodec codec) {
        super(bus, codec);
    }

    @Override
    protected ChannelFuture onStart() throws Exception {
        group = new NioEventLoopGroup();
        childGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(getChannelInitializer());

        return bind(bootstrap, null, bus.getPort());
    }

    @Override
    protected void onStop() throws Exception {
        if (childGroup != null) {
            childGroup.shutdownGracefully();
        }
        super.onStop();
    }
}
