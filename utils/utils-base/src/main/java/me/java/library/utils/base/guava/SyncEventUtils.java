package me.java.library.utils.base.guava;

import com.google.common.eventbus.EventBus;

/**
 * File Name             :  SyncEventUtils
 *
 * @Author :  sylar
 * Create                :  2019-10-21
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public class SyncEventUtils {

    @SuppressWarnings("UnstableApiUsage")
    private static EventBus bus;

    static {
        bus = new EventBus("syncBus");
        bus.register(new DeadEventListener());
    }

    public static void regist(Object listener) {
        bus.register(listener);
    }

    public static void unregist(Object listener) {
        bus.unregister(listener);
    }

    public static void postEvent(Object event) {
        bus.post(event);
    }
}
