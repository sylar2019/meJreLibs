package me.java.library.io.store.modbus;

import com.google.common.base.Preconditions;
import com.serotonin.modbus4j.ip.IpParameters;

/**
 * File Name             :  IpParam
 *
 * @author :  sylar
 * Create                :  2020/7/22
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public class IpParam {
    private String host;
    private int port = 502;

    public IpParam(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public IpParameters convert() {
        Preconditions.checkNotNull(host);

        IpParameters parameters = new IpParameters();
        parameters.setHost(host);
        parameters.setPort(port);
        return parameters;
    }
}
