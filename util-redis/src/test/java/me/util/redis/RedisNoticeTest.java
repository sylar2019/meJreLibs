package me.util.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * File Name             :  RedisNoticeTest
 * Author                :  sylar
 * Create Date           :  2018/4/15
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
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisNoticeTest {

    private final static String TOPIC = "testTopic";

    @Autowired
    private RedisNotice redisNotice;

    private MessageListener messageListener;

    @Before
    public void setUp() throws Exception {
        messageListener = new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                System.out.println("topic:" + new String(pattern) + "message:" + new String(message.getBody()));
            }
        };
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void publish() throws Exception {
        redisNotice.publish(TOPIC, "this is a test message");
        System.out.println("publish success");
    }

    @Test
    public void subscribe() throws Exception {
        redisNotice.subscribe(messageListener, Collections.singletonList(TOPIC));
        System.out.println("subscribe success");

        Thread.sleep(1000 * 60 * 3);
    }

    @Test
    public void unsubscribe() throws Exception {
        redisNotice.unsubscribe(messageListener, Collections.singletonList(TOPIC));
        System.out.println("unsubscribe success");
    }


}