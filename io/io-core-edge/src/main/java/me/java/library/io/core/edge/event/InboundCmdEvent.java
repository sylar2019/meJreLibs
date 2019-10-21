package me.java.library.io.core.edge.event;

import me.java.library.common.event.BaseEvent;
import me.java.library.io.base.Cmd;
import me.java.library.io.core.pipe.Pipe;

/**
 * File Name             :  InboundCmdEvent
 *
 * @Author :  sylar
 * @Create :  2019-10-13
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
public class InboundCmdEvent extends BaseEvent<Pipe, Cmd> {
    public InboundCmdEvent(Pipe pipe, Cmd cmd) {
        super(pipe, cmd);
    }
}
