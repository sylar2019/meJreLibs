package me.java.library.io.core.pipe.list;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.java.library.io.Terminal;
import me.java.library.io.core.bus.list.TcpClientBus;
import me.java.library.io.core.codec.list.TcpCodec;
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
public class TcpClientPipe extends AbstractPipe<TcpClientBus, TcpCodec> {
    public TcpClientPipe(TcpClientBus bus, TcpCodec codec) {
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
