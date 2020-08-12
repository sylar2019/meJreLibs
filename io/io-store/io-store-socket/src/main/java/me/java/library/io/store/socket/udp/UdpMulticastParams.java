package me.java.library.io.store.socket.udp;

import me.java.library.io.base.pipe.BasePipeParams;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.socket.udp.UdpMulticastParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class UdpMulticastParams extends BasePipeParams {

    String groupAddress;
    boolean loopbackModeDisabled = true;
    int ttl = 255;
    boolean reuseAddress = true;
    String networkInterfaceName;

    public UdpMulticastParams(int localPort, String groupAddress) {
        this.localPort = localPort;
        this.groupAddress = groupAddress;
    }

    public String getGroupAddress() {
        return groupAddress;
    }

    public void setGroupAddress(String groupAddress) {
        this.groupAddress = groupAddress;
    }

    public boolean isLoopbackModeDisabled() {
        return loopbackModeDisabled;
    }

    public void setLoopbackModeDisabled(boolean loopbackModeDisabled) {
        this.loopbackModeDisabled = loopbackModeDisabled;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public boolean isReuseAddress() {
        return reuseAddress;
    }

    public void setReuseAddress(boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
    }

    public String getNetworkInterfaceName() {
        return networkInterfaceName;
    }

    public void setNetworkInterfaceName(String networkInterfaceName) {
        this.networkInterfaceName = networkInterfaceName;
    }
}
