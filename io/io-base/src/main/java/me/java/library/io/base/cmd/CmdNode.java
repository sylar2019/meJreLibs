package me.java.library.io.base.cmd;

import me.java.library.common.model.pojo.AbstractIdPojo;
import me.java.library.utils.base.JsonUtils;

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

    protected final static String DEFAULT_CODE = "default";

    protected String code;
    protected CmdType type;
    protected long time;
    protected Terminal from;
    protected Terminal to;

    public CmdNode() {
        this.id = UUID.randomUUID().toString();
        this.time = System.currentTimeMillis();

        this.code = DEFAULT_CODE;
        this.type = CmdType.General;

        this.from = new TerminalNode(UUID.randomUUID().toString(), "unknown");
        this.to = new TerminalNode(UUID.randomUUID().toString(), "unknown");
    }

    public CmdNode(Terminal from, Terminal to) {
        this();
        this.from = from;
        this.to = to;
    }

    public CmdNode(Terminal from, Terminal to, String code) {
        this(from, to);
        this.code = code;
    }

    public CmdNode(Terminal from, Terminal to, String code, CmdType type) {
        this(from, to, code);
        this.type = type;
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
    public String toString() {
        return JsonUtils.toJSONString(this);
    }
}
