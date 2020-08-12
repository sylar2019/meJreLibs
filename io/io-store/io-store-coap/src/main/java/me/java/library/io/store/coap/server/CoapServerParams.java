package me.java.library.io.store.coap.server;

import me.java.library.io.base.pipe.BasePipeParams;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.coap.server.CoapServerParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class CoapServerParams extends BasePipeParams {
    boolean tcp;
    boolean udp;
    int[] ports;

    public CoapServerParams() {
        this(false, true);
    }

    public CoapServerParams(int port) {
        this(false, true, port);
    }

    public CoapServerParams(boolean tcp, boolean udp, int... ports) {
        this.tcp = tcp;
        this.udp = udp;
        this.ports = ports;
    }

    public boolean isTcp() {
        return tcp;
    }

    public void setTcp(boolean tcp) {
        this.tcp = tcp;
    }

    public boolean isUdp() {
        return udp;
    }

    public void setUdp(boolean udp) {
        this.udp = udp;
    }

    public int[] getPorts() {
        return ports;
    }

    public void setPorts(int[] ports) {
        this.ports = ports;
    }
}
