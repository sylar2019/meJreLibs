package me.java.library.utils.event.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executor;
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

    private AsyncEventBus() {
    }

    synchronized public static AsyncEventBus getInstance() {
        return instance;
    }

    @Override
    protected EventBus getBus() {

        Executor executor = new ThreadPoolExecutor(
                2,
                Runtime.getRuntime().availableProcessors() * 2,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("async-bus-pool-%d").build());

        return new com.google.common.eventbus.AsyncEventBus("asyncBus", executor);
    }
}
