package me.java.library.io.store.udp;

import me.java.library.io.common.bus.AbstractSocketBus;
import me.java.library.io.common.bus.BusType;

/**
 * File Name             :  TcpServerBus
 *
 * @author :  sylar
 * Create :  2019-10-15
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class UdpBroadcastBus extends AbstractSocketBus {

    @Override
    public BusType getBusType() {
        return BusType.UdpBroadcast;
    }
}