package me.java.library.io;

import me.java.library.common.model.pojo.AbstractIdPojo;

import java.util.UUID;

/**
 * File Name             :  CmdNode
 *
 * @author :  sylar
 * Create :  2019-10-05
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
public class CmdNode extends AbstractIdPojo<String> implements Cmd {

    protected Terminal from;
    protected Terminal to;
    protected String code;
    protected CmdType type;
    protected long time;

    public CmdNode() {
    }

    public CmdNode(Terminal from, Terminal to, String code) {
        this(from, to, code, CmdType.General);
    }

    public CmdNode(Terminal from, Terminal to, String code, CmdType type) {
        this.id = UUID.randomUUID().toString();
        this.time = System.currentTimeMillis();

        this.from = from;
        this.to = to;
        this.code = code;
        this.type = type;
    }

    @Override
    public Terminal getFrom() {
        return from;
    }

    public void setFrom(Terminal from) {
        this.from = from;
    }

    @Override
    public Terminal getTo() {
        return to;
    }

    public void setTo(Terminal to) {
        this.to = to;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public CmdType getType() {
        return type;
    }

    public void setType(CmdType type) {
        this.type = type;
    }

    @Override
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
