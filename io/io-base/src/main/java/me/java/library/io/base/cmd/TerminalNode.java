package me.java.library.io.base.cmd;

import me.java.library.common.model.pojo.AbstractIdPojo;

import java.util.Objects;
import java.util.UUID;

/**
 * File Name             :  TerminalNode
 *
 * @author :  sylar
 * Create :  2019-10-16
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

    protected final static String DEFAULT_TYPE = "default";

    private final String type;

    public TerminalNode() {
        this(UUID.randomUUID().toString(), DEFAULT_TYPE);
    }

    public TerminalNode(String id) {
        this(id, DEFAULT_TYPE);
    }

    public TerminalNode(String id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return (String.format("%s:%s", type, id)).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TerminalNode) {
            TerminalNode pojo = (TerminalNode) obj;
            return Objects.equals(id, pojo.getId()) && Objects.equals(type, pojo.getType());
        }
        return false;
    }
}
