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
        TcpClientParams params = new TcpClientParams(serverHost, serverPort);
        TcpCodec codec = new TcpCodec(cmdResolver, frameDecoder);
        return new TcpClientPipe(params, codec);
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
        TcpServerParams params = new TcpServerParams(port);
        TcpCodec codec = new TcpCodec(cmdResolver, frameDecoder);
        return new TcpServerPipe(params, codec);
    }

    /**
     * 快速创建 UdpUnicastPipe
     *
     * @param localPort   本地端口
     * @param isBroadcast 是否能广播
     * @param cmdResolver 指令编解码器
     * @return
     */
    public static UdpPeerPipe peer(int localPort, boolean isBroadcast, SimpleCmdResolver cmdResolver) {
        UdpPeerParams params = new UdpPeerParams(localPort, isBroadcast);
        UdpCodec codec = new UdpCodec(cmdResolver);
        return new UdpPeerPipe(params, codec);
    }

    /**
     * 快速创建 UdpMulticastPipe
     *
     * @param localPort
     * @param groupAddress 224.0.2.0～238.255.255.255 为用户可用的组播地址（临时组地址），全网范围内有效；
     * @param cmdResolver
     * @return
     */
    public static UdpMulticastPipe multicast(String networkInterfaceName, int localPort, String groupAddress, SimpleCmdResolver cmdResolver) {
        UdpMulticastParams params = new UdpMulticastParams(localPort, groupAddress);
        params.setNetworkInterfaceName(networkInterfaceName);
        UdpCodec codec = new UdpCodec(cmdResolver);
        return new UdpMulticastPipe(params, codec);
    }

}
