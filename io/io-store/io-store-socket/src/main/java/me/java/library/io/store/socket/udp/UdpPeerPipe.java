package me.java.library.io.store.socket.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import me.java.library.io.core.pipe.AbstractPipe;

/**
 * File Name             :  UdpBroadcastPipe
 *
 * @author :  sylar
 * Create :  2019-10-05
 * Description           :  UDP广播
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class UdpPeerPipe extends AbstractPipe<UdpPeerParams, UdpCodec> {

    public UdpPeerPipe(UdpPeerParams params, UdpCodec codec) {
        super(params, codec);
    }

    @Override
    protected boolean onStart() throws Exception {
        masterLoop = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(masterLoop)
                .channel(NioDatagramChannel.class)
                .handler(channelInitializer)
                .option(ChannelOption.SO_BROADCAST, params.isBroadcast());

        return bind(bootstrap, null, params.getLocalPort()).sync().isDone();
    }

}
