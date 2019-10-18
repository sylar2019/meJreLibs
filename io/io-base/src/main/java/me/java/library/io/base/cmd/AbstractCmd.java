package me.java.library.io.base.cmd;

import me.java.library.common.AbstractIdPojo;
import me.java.library.io.base.Cmd;

import java.util.UUID;

/**
 * File Name             :  AbstractCmd
 *
 * @Author :  sylar
 * @Create :  2019-10-05
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
public abstract class AbstractCmd extends AbstractIdPojo<String> implements Cmd {

    public AbstractCmd() {
        id = UUID.randomUUID().toString();
    }
}
