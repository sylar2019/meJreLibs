package me.java.library.utils.event.guava;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * File Name             :  DeadEventListener
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
public class DeadEventListener {
    @Subscribe
    @AllowConcurrentEvents
    public void listen(DeadEvent event) {
        System.out.println(String.format("###DeadEvent###\nevent:%s",
                event.getEvent().getClass().getName()));

    }
}
