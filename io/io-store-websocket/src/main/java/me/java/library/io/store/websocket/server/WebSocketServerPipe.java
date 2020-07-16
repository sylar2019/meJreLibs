package me.java.library.io.store.websocket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.java.library.io.common.pipe.BasePipe;
import me.java.library.io.store.websocket.WebSocketCmdResolver;

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
public class WebSocketServerPipe extends BasePipe<WebSocketServerBus, WebSocketServerCodec> {

    /**
     * 快速创建 WebSocketServerPipe
     *
     * @param url         服务器端资源url
     *                    url 构成:[scheme:][//host:port][path][?query][#fragment]
     *                    如:  ws://127.0.0.1:8800/ws
     *                    或:  wss://127.0.0.1:8800/ws
     * @param cmdResolver 指令编解码器
     * @return
     */
    public static WebSocketServerPipe express(String url,
                                              WebSocketCmdResolver cmdResolver) {
        WebSocketServerBus bus = new WebSocketServerBus();
        bus.setUrl(url);

        WebSocketServerCodec codec = new WebSocketServerCodec(cmdResolver);
        return new WebSocketServerPipe(bus, codec);
    }

    protected NioEventLoopGroup childGroup;

    public WebSocketServerPipe(WebSocketServerBus bus, WebSocketServerCodec codec) {
        super(bus, codec);
    }

    @Override
    protected ChannelFuture onStartByNetty() throws Exception {
        codec.setBus(bus);

        masterLoop = new NioEventLoopGroup();
        childGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(masterLoop, childGroup)
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
