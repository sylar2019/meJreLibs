package me.java.library.io.store.socket.udp;

import me.java.library.io.base.pipe.BasePipeParams;
import me.java.library.utils.base.NetworkUtils;

import java.net.NetworkInterface;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.socket.udp.UdpMulticastParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class UdpMulticastParams extends BasePipeParams {

    /**
     * 224.0.2.0～238.255.255.255 为用户可用的组播地址（临时组地址），全网范围内有效；
     */
    String groupAddress;
    boolean loopbackModeDisabled = true;
    int ttl = 255;
    boolean reuseAddress = true;
    String networkInterfaceName;

    public UdpMulticastParams(int multicastPort, String groupAddress) {
        this.localPort = multicastPort;
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

    public NetworkInterface getNetworkInterface() {
        try {
            if (networkInterfaceName != null) {
                return NetworkInterface.getByName(networkInterfaceName);
            } else {
                return NetworkUtils.getFirstNetworkInterface();
            }
        } catch (Exception e) {
            return NetworkUtils.getFirstNetworkInterface();
        }
    }
}
