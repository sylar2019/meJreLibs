package me.java.library.io.store.websocket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.java.library.io.core.pipe.AbstractPipe;

/**
 * File Name             :  TcpClientPipe
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
public class WebSocketClientPipe extends AbstractPipe<WebSocketClientParams, WebSocketClientCodec> {

    public WebSocketClientPipe(WebSocketClientParams params, WebSocketClientCodec codec) {
        super(params, codec);
        codec.setParams(params);
    }

    @Override
    protected boolean onStart() throws Exception {
        masterLoop = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(masterLoop)
                .channel(NioSocketChannel.class)
                .handler(channelInitializer)
                .option(ChannelOption.SO_KEEPALIVE, true);

        Channel channel = bootstrap.connect(params.getRemoteHost(), params.getRemotePort()).sync().channel();

        WebSocketClientHandler webSocketClientHandler =
                (WebSocketClientHandler) channel.pipeline().get(WebSocketClientHandler.HANDLER_NAME);
        return webSocketClientHandler.handshakeFuture().sync().isDone();
    }

}
