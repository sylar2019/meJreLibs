package me.java.library.io.base.cmd;

import me.java.library.common.model.pojo.AbstractIdPojo;
import me.java.library.io.base.Cmd;
import me.java.library.io.base.Terminal;
import org.joda.time.DateTime;

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

    protected String code;
    protected CmdType type;
    protected Terminal from;
    protected Terminal to;
    protected long time;
    protected Object tag;

    public AbstractCmd() {
        id = UUID.randomUUID().toString();
        from = Terminal.LOCAL;
        to = Terminal.REMOTE;
        time = DateTime.now().getMillis();
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
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
