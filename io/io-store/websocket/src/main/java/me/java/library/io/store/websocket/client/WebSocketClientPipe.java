package me.java.library.io.store.websocket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.java.library.io.common.pipe.BasePipe;

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
public class WebSocketClientPipe extends BasePipe<WebSocketClientBus, WebSocketClientCodec> {
    public WebSocketClientPipe(WebSocketClientBus bus, WebSocketClientCodec codec) {
        super(bus, codec);
    }

    @Override
    protected ChannelFuture onStartByNetty() throws Exception {
        codec.setBus(bus);

        masterLoop = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(masterLoop)
                .channel(NioSocketChannel.class)
                .handler(getChannelInitializer())
                .option(ChannelOption.SO_KEEPALIVE, true);

        Channel channel = bootstrap.connect(bus.getHost(), bus.getPort()).sync().channel();

        WebSocketClientHandler webSocketClientHandler =
                (WebSocketClientHandler) channel.pipeline().get(WebSocketClientHandler.HANDLER_NAME);
        return webSocketClientHandler.handshakeFuture().sync();
    }

}
