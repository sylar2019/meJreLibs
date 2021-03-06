package me.java.library.utils.base.guava;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * File Name             :  DeadEventListener
 *
 * @author :  sylar
 * Create                :  2019-11-07
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
public class DeadEventListener {
    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    @AllowConcurrentEvents
    public void onEvent(DeadEvent event) {
        System.out.println("###DeadEvent###");
        System.out.println(String.format("source:%s", event.getSource()));
        System.out.println(String.format("event:%s", event.getEvent()));
    }
}
