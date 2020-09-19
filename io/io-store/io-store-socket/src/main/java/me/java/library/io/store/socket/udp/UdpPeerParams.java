package me.java.library.io.store.socket.udp;

import me.java.library.io.base.pipe.BasePipeParams;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.socket.tcp.TcpClientParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class UdpPeerParams extends BasePipeParams {
    boolean isBroadcast;

    public UdpPeerParams(int localPort, boolean isBroadcast) {
        this.localPort = localPort;
        this.isBroadcast = isBroadcast;
    }

    public boolean isBroadcast() {
        return isBroadcast;
    }
}
