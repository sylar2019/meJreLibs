package me.java.library.io.store.websocket.client;

import me.java.library.io.base.pipe.BasePipeParams;

import java.net.URI;
import java.util.Objects;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.websocket.client.WebSocketClientParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class WebSocketClientParams extends BasePipeParams {

    boolean isSsl;
    String contextPath;

    public WebSocketClientParams(String uriPath) {
        URI uri = URI.create(uriPath);
        this.remoteHost = uri.getHost();
        this.remotePort = uri.getPort();
        this.contextPath = uri.getPath();
        this.isSsl = Objects.equals(uri.getScheme(), "wss");
    }

    public WebSocketClientParams(String remoteHost, int remotePort, String contextPath, boolean isSsl) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.contextPath = contextPath;
        this.isSsl = isSsl;
    }

    /**
     * url的构成：
     * [scheme:][//authority][path][?query][#fragment]
     * 或
     * [scheme:][//host:port][path][?query][#fragment]
     * 即：authority = host:port
     *
     * @return
     */
    public String getUriPath() {
        return String.format("%s://%s:%d/%s",
                isSsl ? "wss" : "ws",
                remoteHost,
                remotePort,
                contextPath);
    }

    public boolean isSsl() {
        return isSsl;
    }

    public void setSsl(boolean ssl) {
        isSsl = ssl;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }


}
