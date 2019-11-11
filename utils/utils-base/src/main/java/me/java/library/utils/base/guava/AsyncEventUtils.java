package me.java.library.utils.base.guava;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    private static final TimeUnit KEEP_ALIVE_UNIT = TimeUnit.SECONDS;

    @SuppressWarnings("UnstableApiUsage")
    private static EventBus bus;

    static {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE,
                KEEP_ALIVE_UNIT,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("async-bus-pool-%d").build());

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
