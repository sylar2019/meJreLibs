package me.java.library.io.store.socket.tcp;

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
public class TcpServerPipe extends AbstractPipe<TcpServerBus, TcpCodec> {
    protected NioEventLoopGroup childGroup;

    public TcpServerPipe(TcpServerBus bus, TcpCodec codec) {
        super(bus, codec);
    }

    @Override
    protected boolean onStart() throws Exception {
        masterLoop = new NioEventLoopGroup();
        childGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(masterLoop, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(channelInitializer);

       return bind(bootstrap, bus.getHost(), bus.getPort()).sync().isDone();
    }


    @Override
    protected boolean onStop() throws Exception {
        if (childGroup != null) {
            childGroup.shutdownGracefully().sync();
        }
        return super.onStop();
    }
}
