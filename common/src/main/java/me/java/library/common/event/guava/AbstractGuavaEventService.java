package me.java.library.common.event.guava;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import me.java.library.common.event.Event;
import me.java.library.common.event.EventListener;
import me.java.library.common.event.EventService;

/**
 * File Name             :  AbstractGuavaEventService
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
public abstract class AbstractGuavaEventService implements EventService {

    protected EventBus bus;

    public AbstractGuavaEventService() {
        bus = getBus();
        bus.register(new DeadEventListener());
    }

    protected abstract EventBus getBus();

    @Override
    public void dispose() {
    }

    @Override
    public void regist(EventListener listener) {
        getBus().register(listener);
    }

    @Override
    public void unregist(EventListener listener) {
        getBus().unregister(listener);
    }

    @Override
    public void postEvent(Event<?, ?> event) {
        getBus().post(event);
    }

    public static class DeadEventListener {

        @Subscribe
        @AllowConcurrentEvents
        public void onEvent(DeadEvent event) {
            System.err.println(String.format("###DeadEvent###\nevent:%s", event.getEvent().getClass().getName()));
        }
    }
}
