package me.util.disruptor;

import com.lmax.disruptor.EventHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * File Name             :  DisruptorHubTest
 * Author                :  sylar
 * Create Date           :  2018/4/17
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
public class DisruptorHubTest {
    DisruptorHub disruptorHub;
    EventHandler<ValueEvent> eventEventHandler;

    @Before
    public void setUp() throws Exception {
        eventEventHandler = new EventHandler<ValueEvent>() {
            @Override
            public void onEvent(ValueEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println(sequence + " :" + event.getValue().toString() + " " + endOfBatch);
            }
        };

        disruptorHub = new DisruptorHub(eventEventHandler);
    }

    @After
    public void tearDown() throws Exception {
        stop();
    }

    @Test
    public void stop() throws Exception {
        disruptorHub.stop();
    }

    @Test
    public void publish() throws Exception {
        for (int i = 0; i < 100; i++) {
            disruptorHub.publish("this is a test message");
        }
    }


}