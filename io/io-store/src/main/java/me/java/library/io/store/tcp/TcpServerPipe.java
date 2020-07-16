package me.java.library.io.store.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.java.library.io.common.codec.SimpleCmdResolver;
import me.java.library.io.common.pipe.BasePipe;

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
public class TcpServerPipe extends BasePipe<TcpServerBus, TcpCodec> {

    /**
     * 快速创建 TcpServerPipe
     *
     * @param port         绑定的端口
     * @param frameDecoder 帧解析器
     * @param cmdResolver  指令编解码器
     * @return
     */
    public static TcpServerPipe express(int port,
                                        ByteToMessageDecoder frameDecoder,
                                        SimpleCmdResolver cmdResolver) {
        TcpServerBus bus = new TcpServerBus();
        bus.setUrl(String.format("tcp://localhost:%d", port));

        TcpCodec codec = new TcpCodec(cmdResolver, frameDecoder);
        return new TcpServerPipe(bus, codec);
    }

    protected NioEventLoopGroup childGroup;

    public TcpServerPipe(TcpServerBus bus, TcpCodec codec) {
        super(bus, codec);
    }

    @Override
    protected ChannelFuture onStartByNetty() throws Exception {
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
