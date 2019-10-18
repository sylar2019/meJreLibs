package me.java.library.utils.event.guava;

import com.google.common.eventbus.EventBus;
import me.java.library.utils.event.IEvent;

/**
 * File Name             :  AbstractEventBus
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
public abstract class AbstractEventBus implements IEventBus {

    protected EventBus bus;

    public AbstractEventBus() {
        bus = getBus();
        bus.register(new DeadEventListener());
    }

    protected abstract EventBus getBus();

    @Override
    public void regist(Object listener) {
        getBus().register(listener);
    }

    @Override
    public void unregist(Object listener) {
        getBus().unregister(listener);
    }

    @Override
    public void postEvent(IEvent<?, ?> event) {
        getBus().post(event);
    }
}
