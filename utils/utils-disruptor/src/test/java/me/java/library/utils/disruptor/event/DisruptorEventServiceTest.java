package me.java.library.utils.disruptor.event;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.PreDestroy;

/**
 * @author : sylar
 * @fullName : me.java.library.utils.disruptor.event.DisruptorEventServiceTest
 * @createDate : 2020/7/29
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class DisruptorEventServiceTest implements EventListener {

    DisruptorEventService eventService;

    @Before
    public void init() {
        eventService = new DisruptorEventService();
        eventService.regist(this);
    }

    @PreDestroy
    public void dispose() {
        eventService.unregist(this);
        eventService.dispose();
    }

    @Test
    public void postEvent() throws InterruptedException {
        DisruptorEvent event = new DisruptorEvent();
        event.setSource("i am source");
        event.setContent("i am content");
        eventService.postEvent(event);

        Event<?, ?> event2 = new Event<Class<?>, DateTime>() {
            @Override
            public Class<?> getSource() {
                return getClass();
            }

            @Override
            public DateTime getContent() {
                return DateTime.now();
            }
        };

        eventService.postEvent(event2);

        Thread.sleep(2000);
    }

    @Override
    public void onEvent(Event<?, ?> event) {
        System.out.println("### source ### :" + event.getSource());
        System.out.println("### content ### :" + event.getContent());
    }
}