package me.java.library.io.store.socket.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.channel.socket.nio.NioDatagramChannel;
import me.java.library.io.core.pipe.AbstractPipe;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * File Name             :  UdpMulticastPipe
 * <p>
 * 组播组可以是永久的也可以是临时的。组播组地址中，有一部分由官方分配的，称为永久组播组。永久组播组保持不变的是它的ip地址，组中的成员构成可以发生变化。永久组播组中成员的数量都可以是任意的，甚至可以为零。那些没有保留下来供永久组播组使用的ip组播地址，可以被临时组播组利用。
 * <p>
 * 224.0.0.0～224.0.0.255     为预留的组播地址（永久组地址），地址224.0.0.0保留不做分配，其它地址供路由协议使用；
 * 224.0.1.0～224.0.1.255     是公用组播地址，Internetwork Control Block；
 * 224.0.2.0～238.255.255.255 为用户可用的组播地址（临时组地址），全网范围内有效；
 * 239.0.0.0～239.255.255.255 为本地管理组播地址，仅在特定的本地范围内有效。
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
public class UdpMulticastPipe extends AbstractPipe<UdpMulticastParams, UdpCodec> {

    public UdpMulticastPipe(UdpMulticastParams params, UdpCodec codec) {
        super(params, codec);
    }

    @Override
    protected boolean onStart() throws Exception {
        masterLoop = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(masterLoop)
                .channelFactory((ChannelFactory<NioDatagramChannel>) () -> new NioDatagramChannel(InternetProtocolFamily.IPv4))
                .handler(channelInitializer)
                .option(ChannelOption.IP_MULTICAST_LOOP_DISABLED, params.isLoopbackModeDisabled())
                .option(ChannelOption.IP_MULTICAST_TTL, params.getTtl())
                .option(ChannelOption.IP_MULTICAST_IF, params.getNetworkInterface())
                .option(ChannelOption.SO_REUSEADDR, params.isReuseAddress());

        ChannelFuture future = bind(bootstrap, null, params.getLocalPort()).sync();

        InetAddress groupAddress = InetAddress.getByName(params.getGroupAddress());
        future = ((NioDatagramChannel) future.channel()).joinGroup(groupAddress);
        return future.sync().isDone();
    }
}
