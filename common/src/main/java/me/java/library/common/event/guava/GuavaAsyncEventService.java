package me.java.library.common.event.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * File Name             :  GuavaAsyncEventService
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
public class GuavaAsyncEventService extends AbstractGuavaEventService {

    private static GuavaAsyncEventService instance = new GuavaAsyncEventService();

    private GuavaAsyncEventService() {
    }

    synchronized public static GuavaAsyncEventService getInstance() {
        return instance;
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
