package me.java.library.utils.event.guava;

import com.google.common.eventbus.EventBus;

/**
 * File Name             :  SyncEventBus
 *
 * @Author :  sylar
 * @Create :  2019-10-08
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
public class SyncEventBus extends AbstractEventBus {

    private static SyncEventBus instance = new SyncEventBus();
    private EventBus bus = new EventBus();

    private SyncEventBus() {
    }

    synchronized public static SyncEventBus getInstance() {
        return instance;
    }

    @Override
    protected EventBus getBus() {
        return new EventBus("syncBus");
    }
}
