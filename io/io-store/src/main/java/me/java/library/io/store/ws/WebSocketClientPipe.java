package me.java.library.io.store.ws;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.java.library.io.common.cmd.Terminal;
import me.java.library.io.common.pipe.AbstractPipe;

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
public class WebSocketClientPipe extends AbstractPipe<WebSocketClientBus, WebSocketClientCodec> {
    public WebSocketClientPipe(WebSocketClientBus bus, WebSocketClientCodec codec) {
        super(bus, codec);
    }

    @Override
    protected ChannelFuture onStart() throws Exception {
        group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(getChannelInitializer())
                .option(ChannelOption.SO_KEEPALIVE, true);

        return bootstrap.connect(bus.getHost(), bus.getPort());
    }

    @Override
    protected void onConnectionChanged(Terminal terminal, boolean isConnected) {
        super.onConnectionChanged(terminal, isConnected);
        if (!isConnected) {
            restart();
        }
    }
}
