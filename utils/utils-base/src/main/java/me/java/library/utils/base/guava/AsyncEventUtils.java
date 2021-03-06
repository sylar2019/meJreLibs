package me.java.library.utils.base.guava;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import me.java.library.utils.base.ConcurrentUtils;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * File Name             :  SyncEventUtils
 *
 * @author :  sylar
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
public class AsyncEventUtils {

    @SuppressWarnings("UnstableApiUsage")
    private static EventBus bus;

    static {
        ThreadPoolExecutor executor = ConcurrentUtils.simpleThreadPool("async-bus");
        executor.allowCoreThreadTimeOut(true);

        bus = new AsyncEventBus("asyncBus", executor);
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
