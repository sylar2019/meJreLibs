package me.java.library.io.store.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.java.library.io.common.codec.SimpleCmdResolver;
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
public class TcpClientPipe extends BasePipe<TcpClientBus, TcpCodec> {
    /**
     * 快速创建 TcpClientPipe
     *
     * @param serverHost   服务器地址
     * @param serverPort   服务器端口
     * @param frameDecoder 帧解析器
     * @param cmdResolver  指令编解码器
     * @return
     */
    public static TcpClientPipe express(String serverHost,
                                        int serverPort,
                                        ByteToMessageDecoder frameDecoder,
                                        SimpleCmdResolver cmdResolver) {
        TcpClientBus bus = new TcpClientBus();
        bus.setUrl(String.format("tcp://%s:%d", serverHost, serverPort));

        TcpCodec codec = new TcpCodec(cmdResolver, frameDecoder);
        return new TcpClientPipe(bus, codec);
    }


    public TcpClientPipe(TcpClientBus bus, TcpCodec codec) {
        super(bus, codec);
    }

    @Override
    protected ChannelFuture onStartByNetty() throws Exception {
        masterLoop = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(masterLoop)
                .channel(NioSocketChannel.class)
                .handler(getChannelInitializer())
                .option(ChannelOption.SO_KEEPALIVE, true);

        return bootstrap.connect(bus.getHost(), bus.getPort());
    }

}
