package me.java.library.io.store.socket;

import io.netty.handler.codec.ByteToMessageDecoder;
import me.java.library.io.core.codec.SimpleCmdResolver;
import me.java.library.io.store.socket.tcp.*;
import me.java.library.io.store.socket.udp.*;

/**
 * File Name             :  SocketExpress
 *
 * @author :  sylar
 * Create                :  2020/7/22
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public class SocketExpress {

    /**
     * 快速创建 TcpClientPipe
     *
     * @param serverHost   服务器地址
     * @param serverPort   服务器端口
     * @param frameDecoder 帧解析器
     * @param cmdResolver  指令编解码器
     * @return
     */
    public static TcpClientPipe tcpClient(String serverHost, int serverPort, ByteToMessageDecoder frameDecoder, SimpleCmdResolver cmdResolver) {
        TcpClientBus bus = new TcpClientBus();
        bus.setUrl(String.format("tcp://%s:%d", serverHost, serverPort));

        TcpCodec codec = new TcpCodec(cmdResolver, frameDecoder);
        return new TcpClientPipe(bus, codec);
    }

    /**
     * 快速创建 TcpServerPipe
     *
     * @param port         绑定的端口
     * @param frameDecoder 帧解析器
     * @param cmdResolver  指令编解码器
     * @return
     */
    public static TcpServerPipe tcpServer(int port, ByteToMessageDecoder frameDecoder, SimpleCmdResolver cmdResolver) {
        TcpServerBus bus = new TcpServerBus();
        bus.setUrl(String.format("tcp://localhost:%d", port));

        TcpCodec codec = new TcpCodec(cmdResolver, frameDecoder);
        return new TcpServerPipe(bus, codec);
    }

    /**
     * 快速创建 UdpBroadcastPipe
     *
     * @param port        本地端口
     * @param cmdResolver 指令编解码器
     * @return
     */
    public static UdpBroadcastPipe udpBroadcast(int port, SimpleCmdResolver cmdResolver) {
        UdpBroadcastBus bus = new UdpBroadcastBus();
        bus.setUrl(String.format("udp://localhost:%d", port));

        UdpCodec codec = new UdpCodec(cmdResolver);
        return new UdpBroadcastPipe(bus, codec);
    }


    /**
     * 快速创建 UdpMulticastPipe
     *
     * @param networkInterfaceName 网络接口名称
     * @param port                 本地端口
     * @param cmdResolver          指令编解码器
     * @return
     */
    public static UdpMulticastPipe udpMulticast(String networkInterfaceName, int port, SimpleCmdResolver cmdResolver) {
        UdpMulticastBus bus = new UdpMulticastBus();
        bus.setNetworkInterfaceName(networkInterfaceName);
        bus.setUrl(String.format("udp://localhost:%d", port));

        UdpCodec codec = new UdpCodec(cmdResolver);
        return new UdpMulticastPipe(bus, codec);
    }


    /**
     * 快速创建 UdpUnicastPipe
     *
     * @param port        本地端口
     * @param cmdResolver 指令编解码器
     * @return
     */
    public static UdpUnicastPipe udpUnicast(int port, SimpleCmdResolver cmdResolver) {
        UdpUnicastBus bus = new UdpUnicastBus();
        bus.setUrl(String.format("udp://localhost:%d", port));

        UdpCodec codec = new UdpCodec(cmdResolver);
        return new UdpUnicastPipe(bus, codec);
    }

}
