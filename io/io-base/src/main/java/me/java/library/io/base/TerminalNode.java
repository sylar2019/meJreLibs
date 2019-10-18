package me.java.library.io.base;

import me.java.library.common.AbstractIdPojo;

/**
 * File Name             :  TerminalNode
 *
 * @Author :  sylar
 * @Create :  2019-10-16
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
public class TerminalNode extends AbstractIdPojo<String> implements Terminal {

    private String type;

    public TerminalNode(String id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }
}
