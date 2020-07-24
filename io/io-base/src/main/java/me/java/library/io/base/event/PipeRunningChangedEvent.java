package me.java.library.io.base.event;

import me.java.library.io.base.pipe.Pipe;
import me.java.library.utils.base.guava.AbstractEvent;

/**
 * File Name             :  InboundCmdEvent
 *
 * @author :  sylar
 * Create :  2019-10-13
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
public class PipeRunningChangedEvent extends AbstractEvent<Pipe, Boolean> {
    public PipeRunningChangedEvent(Pipe pipe, Boolean isRunning) {
        super(pipe, isRunning);
    }
}
