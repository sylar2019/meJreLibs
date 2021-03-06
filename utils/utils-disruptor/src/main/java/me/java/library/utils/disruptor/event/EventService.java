package me.java.library.utils.disruptor.event;

import me.java.library.common.service.Serviceable;

/**
 * File Name             :  EventService
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
public interface EventService extends Serviceable {

    void regist(final EventListener listener);

    void unregist(final EventListener listener);

    void postEvent(final Event<?, ?> event);
}
