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
public class TcpServerParams extends BasePipeParams {
    public TcpServerParams(int localPort) {
        this.localPort = localPort;
    }
}
