package me.java.library.io.store.socket.tcp;

import io.netty.bootstrap.Bootstrap;
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
public class TcpClientPipe extends AbstractPipe<TcpClientParams, TcpCodec> {

    public TcpClientPipe(TcpClientParams params, TcpCodec codec) {
        super(params, codec);
    }

    @Override
    protected boolean onStart() throws Exception {
        masterLoop = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(masterLoop)
                .channel(NioSocketChannel.class)
                .handler(channelInitializer)
                .option(ChannelOption.SO_KEEPALIVE, true);

        return bootstrap.connect(params.getRemoteHost(), params.getRemotePort()).sync().isDone();
    }
}
