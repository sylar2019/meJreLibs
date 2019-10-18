package me.java.library.io.base;

import me.java.library.utils.base.JvmUtils;
import me.java.library.utils.base.NetworkUtils;

/**
 * File Name             :  HostNode
 *
 * @Author :  sylar
 * @Create :  2019-10-17
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

    protected String hostCode;

    public HostNode(String hostCode) {
        this.hostCode = hostCode;
    }

    @Override
    public String getHostCode() {
        return hostCode;
    }

    @Override
    public String getHostName() {
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
