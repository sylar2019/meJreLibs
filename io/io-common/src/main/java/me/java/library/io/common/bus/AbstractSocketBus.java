package me.java.library.io.common.bus;

/**
 * File Name             :  AbstractSocketBus
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
public abstract class AbstractSocketBus extends AbstractBus {

    public final static String defaultHost = "localhost";
    public final static String anyHost = "0.0.0.0";
    public final static String defaultMulticastHost = "239.255.27.1";
    public final static String defaultBroadcastHost = "255.255.255.255";
    public final static int defaultPort = 0;

    public String getHost() {
        return getOrDefault(Bus.BUS_ATTR_SOCKET_HOST, defaultHost);
    }

    public void setHost(String host) {
        setAttr(Bus.BUS_ATTR_SOCKET_HOST, host);
    }

    public String getHost(String defaultValue) {
        return getOrDefault(Bus.BUS_ATTR_SOCKET_HOST, defaultValue);
    }

    public int getPort() {
        return getOrDefault(Bus.BUS_ATTR_SOCKET_PORT, defaultPort);
    }

    public void setPort(int port) {
        setAttr(Bus.BUS_ATTR_SOCKET_PORT, port);
    }

    public int getPort(int defaultValue) {
        return getOrDefault(Bus.BUS_ATTR_SOCKET_PORT, defaultValue);
    }
}
