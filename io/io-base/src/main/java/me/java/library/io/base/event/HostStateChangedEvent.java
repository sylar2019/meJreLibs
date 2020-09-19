package me.java.library.io.base.event;

import me.java.library.io.base.cmd.Host;
import me.java.library.utils.base.guava.AbstractEvent;

/**
 * File Name             :  HostStateChangedEvent
 *
 * @author :  sylar
 * Create :  2019-10-17
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
public class HostStateChangedEvent extends AbstractEvent<Host, Boolean> {

    public HostStateChangedEvent(Host host, Boolean isRunning) {
        super(host, isRunning);
    }
}
