package me.java.library.io.common.bus;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    public final static String BUS_ATTR_SOCKET_HOST = "host";
    public final static String BUS_ATTR_SOCKET_PORT = "port";
    public final static String BUS_ATTR_SOCKET_PATH = "path";
    public final static String BUS_ATTR_SOCKET_NETWORK_INTERFACE = "interface";

    public final static String DEFAULT_HOST = "localhost";
    public final static int DEFAULT_PORT = 0;
    public final static String DEFAULT_PATH = "/";
    public final static String DEFAULT_MULTICAST_HOST = "239.255.27.1";
    public final static String DEFAULT_BROADCAST_HOST = "255.255.255.255";
    public final static String ANY_HOST = "0.0.0.0";

    @JsonIgnore
    public String getHost() {
        return getOrDefault(BUS_ATTR_SOCKET_HOST, DEFAULT_HOST);
    }

    public void setHost(String host) {
        setAttr(BUS_ATTR_SOCKET_HOST, host);
    }

    public String getHost(String defaultValue) {
        return getOrDefault(BUS_ATTR_SOCKET_HOST, defaultValue);
    }

    @JsonIgnore
    public int getPort() {
        return getOrDefault(BUS_ATTR_SOCKET_PORT, DEFAULT_PORT);
    }

    public void setPort(int port) {
        setAttr(BUS_ATTR_SOCKET_PORT, port);
    }

    public int getPort(int defaultValue) {
        return getOrDefault(BUS_ATTR_SOCKET_PORT, defaultValue);
    }

    @JsonIgnore
    public String getSocketPath() {
        return getOrDefault(BUS_ATTR_SOCKET_PATH, DEFAULT_PATH);
    }

    public void setSocketPath(String path) {
        setAttr(BUS_ATTR_SOCKET_PATH, path);
    }
}
