package me.java.library.io.store.websocket.server;

import me.java.library.io.base.pipe.BasePipeParams;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.websocket.client.WebSocketClientParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class WebSocketServerParams extends BasePipeParams {

    boolean isSsl;
    String contextPath;
    String sslCertFile;
    String sslKeyFile;

    public WebSocketServerParams(int localPort, String contextPath, boolean isSsl) {
        this.localPort = localPort;
        this.contextPath = contextPath;
        this.isSsl = isSsl;
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

    public String getSslCertFile() {
        return sslCertFile;
    }

    public void setSslCertFile(String sslCertFile) {
        this.sslCertFile = sslCertFile;
    }

    public String getSslKeyFile() {
        return sslKeyFile;
    }

    public void setSslKeyFile(String sslKeyFile) {
        this.sslKeyFile = sslKeyFile;
    }
}
