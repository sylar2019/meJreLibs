package me.java.library.io.store.socket.tcp;

import me.java.library.io.base.pipe.BasePipeParams;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.socket.tcp.TcpClientParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class TcpClientParams extends BasePipeParams {
    public TcpClientParams(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }
}
