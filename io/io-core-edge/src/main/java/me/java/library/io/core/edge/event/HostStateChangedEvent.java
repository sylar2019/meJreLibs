package me.java.library.io.core.edge.event;

import me.java.library.common.event.BaseEvent;
import me.java.library.io.base.Host;

/**
 * File Name             :  HostStateChangedEvent
 *
 * @Author :  sylar
 * @Create :  2019-10-17
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
public class HostStateChangedEvent extends BaseEvent<Host, Boolean> {

    public HostStateChangedEvent(Host host, Boolean isRunning) {
        super(host, isRunning);
    }
}