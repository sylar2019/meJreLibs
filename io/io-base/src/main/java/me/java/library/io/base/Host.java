package me.java.library.io.base;

import java.io.Serializable;

/**
 * File Name             :  Host
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
public interface Host extends Serializable {

    String getHostCode();

    String getHostName();

    String getMac();

    String getIP();

    int getPID();
}
