package me.java.library.io.common.cmd;

import java.io.Serializable;

/**
 * File Name             :  Host
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
public interface Host extends Serializable {

    Host LOCAL = new HostNode();

    String getCode();

    String getName();

    String getMac();

    String getIP();

    int getPID();
}
