package me.java.library.io.base.cmd;

import me.java.library.utils.base.JvmUtils;
import me.java.library.utils.base.NetworkUtils;

import java.util.Objects;

/**
 * File Name             :  HostNode
 *
 * @author :  sylar
 * Create :  2019-10-17
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
public class HostNode implements Host {

    protected final static String DEFAULT_CODE = "default";

    protected String hostCode;

    public HostNode() {
        this(DEFAULT_CODE);
    }

    public HostNode(String hostCode) {
        this.hostCode = hostCode;
    }

    @Override
    public int hashCode() {
        return hostCode.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HostNode) {
            HostNode pojo = (HostNode) obj;
            return Objects.equals(hostCode, pojo.getCode());
        }
        return super.equals(obj);
    }

    @Override
    public String getCode() {
        return hostCode;
    }

    @Override
    public String getName() {
        return NetworkUtils.getHostName();
    }

    @Override
    public String getMac() {
        return NetworkUtils.getHostMac();
    }

    @Override
    public String getIP() {
        return NetworkUtils.getHostIP();
    }

    @Override
    public int getPID() {
        return JvmUtils.getPID();
    }
}
