package me.java.library.io.store.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.channel.socket.nio.NioDatagramChannel;
import me.java.library.io.common.bus.AbstractSocketBus;
import me.java.library.io.common.pipe.AbstractPipe;

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
        String multicastHost = bus.getHost(AbstractSocketBus.DEFAULT_MULTICAST_HOST);
        int multicastPort = bus.getPort();
        InetSocketAddress multicastAddress = new InetSocketAddress(multicastHost, multicastPort);

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channelFactory((ChannelFactory<NioDatagramChannel>) () -> new NioDatagramChannel(InternetProtocolFamily.IPv4))
                .handler(getChannelInitializer())
                .option(ChannelOption.IP_MULTICAST_LOOP_DISABLED, false)
                .option(ChannelOption.IP_MULTICAST_TTL, 255)
                .option(ChannelOption.IP_MULTICAST_IF, networkInterface)
                .option(ChannelOption.SO_REUSEADDR, true);

        ChannelFuture future = bind(bootstrap, null, multicastPort).sync();
        future = ((NioDatagramChannel) future.channel()).joinGroup(multicastAddress, networkInterface);
        return future;
    }
}
