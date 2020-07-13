package me.java.library.io.common.edge.event;

import me.java.library.io.common.pipe.Pipe;
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
public class PipeExceptionEvent extends AbstractEvent<Pipe, Throwable> {
    public PipeExceptionEvent(Pipe pipe, Throwable throwable) {
        super(pipe, throwable);
    }
}
