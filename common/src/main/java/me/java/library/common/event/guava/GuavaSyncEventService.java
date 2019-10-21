package me.java.library.common.event.guava;

import com.google.common.eventbus.EventBus;

/**
 * File Name             :  GuavaSyncEventService
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
public class GuavaSyncEventService extends AbstractGuavaEventService {

    private static GuavaSyncEventService instance = new GuavaSyncEventService();

    private GuavaSyncEventService() {
    }

    synchronized public static GuavaSyncEventService getInstance() {
        return instance;
    }

    @Override
    protected EventBus getBus() {
        return new EventBus("syncBus");
    }
}
