package me.java.library.io.store.lwm2m.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.java.library.io.base.cmd.CmdNode;
import org.eclipse.leshan.core.node.LwM2mNode;
import org.eclipse.leshan.core.node.LwM2mPath;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.lwm2m.common.Lwm2mCmd
 * @createDate : 2020/8/17
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class Lwm2mCmd extends CmdNode {
    private final static String ATTR_PTAH = "lwm2m-path";
    private final static String ATTR_CONTENT = "lwm2m-content";

    @JsonIgnore
    public LwM2mPath getPath() {
        return getAttr(ATTR_PTAH);
    }

    public void setPath(LwM2mPath path) {
        setAttr(ATTR_PTAH, path);
    }

    @JsonIgnore
    public LwM2mNode getContent() {
        return getAttr(ATTR_CONTENT);
    }

    public void setContent(LwM2mNode content) {
        setAttr(ATTR_CONTENT, content);
    }
}
