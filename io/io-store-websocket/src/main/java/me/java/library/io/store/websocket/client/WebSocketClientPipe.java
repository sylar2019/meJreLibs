package me.java.library.io.store.websocket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.java.library.io.common.pipe.BasePipe;
import me.java.library.io.store.websocket.WebSocketCmdResolver;

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
    /**
     * 快速创建 WebSocketClientPipe
     *
     * @param url         服务器端资源url,
     *                    url 构成:[scheme:][//host:port][path][?query][#fragment]
     *                    如:  ws://127.0.0.1:8800/ws
     *                    或:  wss://127.0.0.1:8800/ws
     * @param cmdResolver 指令编解码器
     * @return
     */
    public static WebSocketClientPipe express(String url,
                                              WebSocketCmdResolver cmdResolver) {
        WebSocketClientBus bus = new WebSocketClientBus();
        bus.setUrl(url);

        WebSocketClientCodec codec = new WebSocketClientCodec(cmdResolver);
        return new WebSocketClientPipe(bus, codec);
    }

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
