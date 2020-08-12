package me.java.library.io.store.opc.client;

import me.java.library.io.base.pipe.BasePipeParams;
import org.openscada.opc.lib.common.ConnectionInformation;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.opc.client.OpcClientParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class OpcClientParams extends BasePipeParams {
    private String server = "localhost";
    private String domain = "localhost";
    private String user;
    private String password;
    private String clsId;
    private String progId;

    public ConnectionInformation convert() {
        ConnectionInformation ci = new ConnectionInformation();
        ci.setHost(server);
        ci.setDomain(domain);
        ci.setUser(user);
        ci.setPassword(password);
        ci.setProgId(progId);
        ci.setClsid(clsId);
        return ci;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClsId() {
        return clsId;
    }

    public void setClsId(String clsId) {
        this.clsId = clsId;
    }

    public String getProgId() {
        return progId;
    }

    public void setProgId(String progId) {
        this.progId = progId;
    }
}
