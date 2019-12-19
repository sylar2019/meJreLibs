package me.java.library.io.core.pipe.list;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.channel.socket.nio.NioDatagramChannel;
import me.java.library.io.core.bus.AbstractSocketBus;
import me.java.library.io.core.bus.list.UdpMulticastBus;
import me.java.library.io.core.codec.list.UdpCodec;
import me.java.library.io.core.pipe.AbstractPipe;

import java.net.InetSocketAddress;
import java.net.NetworkInterface;

/**
 * File Name             :  UdpMulticastPipe
 *
 * @author :  sylar
 * Create :  2019-10-05
 * Description           :  UDP组播
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class UdpMulticastPipe extends AbstractPipe<UdpMulticastBus, UdpCodec> {

    public UdpMulticastPipe(UdpMulticastBus bus, UdpCodec codec) {
        super(bus, codec);
    }

    @Override
    protected ChannelFuture onStart() throws Exception {
        group = new NioEventLoopGroup();

        String networkInterfaceName = bus.getNetworkInterfaceName();
        NetworkInterface networkInterface = NetworkInterface.getByName(networkInterfaceName);
        String multicastHost = bus.getHost(AbstractSocketBus.defaultMulticastHost);
        int multicastPort = bus.getPort();
        InetSocketAddress multicastAddress = new InetSocketAddress(multicastHost, multicastPort);

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channelFactory(new ChannelFactory<NioDatagramChannel>() {
                    @Override
                    public NioDatagramChannel newChannel() {
                        return new NioDatagramChannel(InternetProtocolFamily.IPv4);
                    }
                })
                .channel(NioDatagramChannel.class)
                .handler(getChannelInitializer())
                .option(ChannelOption.IP_MULTICAST_LOOP_DISABLED, false)
                .option(ChannelOption.IP_MULTICAST_TTL, 255)
                .option(ChannelOption.IP_MULTICAST_IF, networkInterface)
                .option(ChannelOption.SO_REUSEADDR, true);

        ChannelFuture future = bind(bootstrap, AbstractSocketBus.anyHost, multicastPort);
        return ((NioDatagramChannel) future.channel()).joinGroup(multicastAddress, networkInterface);
    }
}
