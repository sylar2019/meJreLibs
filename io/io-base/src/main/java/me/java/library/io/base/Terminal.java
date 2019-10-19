package me.java.library.io.base;

import me.java.library.common.Attributable;
import me.java.library.common.IDPojo;

/**
 * File Name             :  Terminal
 *
 * @Author :  sylar
 * @Create :  2019-10-05
 * Description           : 通讯终端
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public interface Terminal extends IDPojo<String>, Attributable<String, Object> {

    String getType();

    Terminal LOCAL = new TerminalNode("local", "default");
    Terminal REMOTE = new TerminalNode("remote", "default");
}
