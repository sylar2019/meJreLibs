package me.java.library.io.base.pipe;

import me.java.library.io.base.cmd.Host;

import java.time.Duration;

/**
 * @author : sylar
 * @fullName : me.java.library.io.base.pipe.SocketParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class BasePipeParams implements PipeParams {
    protected Host host = Host.LOCAL;
    protected boolean isDaemon = true;
    protected Duration daemonDuration = Duration.ofSeconds(5);
    protected boolean isEventEnabled = true;

    protected String localHost = "0.0.0.0";
    protected Integer localPort = 0;
    protected String remoteHost = "0.0.0.0";
    protected Integer remotePort = 0;

    @Override
    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    @Override
    public boolean isDaemon() {
        return isDaemon;
    }

    public void setDaemon(boolean daemon) {
        isDaemon = daemon;
    }

    public Duration getDaemonDuration() {
        return daemonDuration;
    }

    public void setDaemonDuration(Duration daemonDuration) {
        this.daemonDuration = daemonDuration;
    }

    @Override
    public boolean isEventEnabled() {
        return isEventEnabled;
    }

    public void setEventEnabled(boolean eventEnabled) {
        isEventEnabled = eventEnabled;
    }

    public String getLocalHost() {
        return localHost;
    }

    public void setLocalHost(String localHost) {
        this.localHost = localHost;
    }

    public Integer getLocalPort() {
        return localPort;
    }

    public void setLocalPort(Integer localPort) {
        this.localPort = localPort;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public Integer getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }
}
