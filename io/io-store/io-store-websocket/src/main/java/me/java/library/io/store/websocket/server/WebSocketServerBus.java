package me.java.library.io.store.websocket.server;

import me.java.library.io.core.bus.BusType;
import me.java.library.io.store.websocket.WebSocketBus;

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
public class WebSocketServerBus extends WebSocketBus {
    @Override
    public BusType getBusType() {
        return BusType.WebSocketServer;
    }
}
