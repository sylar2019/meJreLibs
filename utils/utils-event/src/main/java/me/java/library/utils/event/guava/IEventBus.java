package me.java.library.utils.event.guava;

import me.java.library.utils.event.IEvent;

/**
 * File Name             :  IEventBus
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
public interface IEventBus {

    void regist(Object listener);

    void unregist(Object listener);

    void postEvent(final IEvent<?, ?> event);
}
