package me.java.library.utils.base;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * File Name             :  ConcurrentUtils
 *
 * @author :  sylar
 * Create                :  2020/7/10
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
public class ConcurrentUtils {

    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2;
    private static final int KEEP_ALIVE = 1;
    private static final TimeUnit KEEP_ALIVE_UNIT = TimeUnit.SECONDS;

    private ConcurrentUtils() {
        throw new AssertionError();
    }

    public static ThreadPoolExecutor simpleThreadPool() {
        return simpleThreadPool("simple", new LinkedBlockingQueue<>());
    }

    public static ThreadPoolExecutor simpleThreadPool(String poolName) {
        return simpleThreadPool(poolName, new LinkedBlockingQueue<>());
    }

    public static ThreadPoolExecutor simpleThreadPool(String poolName, BlockingQueue<Runnable> queue) {

        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE,
                KEEP_ALIVE_UNIT,
                queue,
                new ThreadFactoryBuilder().setNameFormat(poolName + "-pool-%d").build());
    }

    public static ScheduledThreadPoolExecutor simpleScheduledThreadPool() {
        return simpleScheduledThreadPool("simple");
    }

    public static ScheduledThreadPoolExecutor simpleScheduledThreadPool(String poolName) {
        return new ScheduledThreadPoolExecutor(
                CORE_POOL_SIZE,
                new ThreadFactoryBuilder().setNameFormat(poolName + "-scheduled-pool-%d").build()
        );
    }
}
