package me.java.library.io.store.websocket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.java.library.io.core.pipe.AbstractPipe;

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
public class WebSocketServerPipe extends AbstractPipe<WebSocketServerParams, WebSocketServerCodec> {
    protected NioEventLoopGroup childGroup;

    public WebSocketServerPipe(WebSocketServerParams params, WebSocketServerCodec codec) {
        super(params, codec);
        codec.setParams(params);
    }

    @Override
    protected boolean onStart() throws Exception {
        masterLoop = new NioEventLoopGroup();
        childGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(masterLoop, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(channelInitializer);

        return bind(bootstrap, null, params.getLocalPort()).sync().isDone();
    }

    @Override
    protected boolean onStop() throws Exception {
        if (childGroup != null) {
            childGroup.shutdownGracefully();
        }
        return super.onStop();
    }
}
