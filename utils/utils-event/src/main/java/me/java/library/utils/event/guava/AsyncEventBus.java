package me.java.library.utils.event.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
public class AsyncEventBus extends AbstractEventBus {

    private static AsyncEventBus instance = new AsyncEventBus();

    synchronized public static AsyncEventBus getInstance() {
        return instance;
    }

    private AsyncEventBus() {
    }

    @Override
    protected EventBus getBus() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() * 2,
                8,
                100L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("async-bus-pool-%d").build());

        executor.allowCoreThreadTimeOut(true);

        return new com.google.common.eventbus.AsyncEventBus("asyncBus", executor);
    }
}
