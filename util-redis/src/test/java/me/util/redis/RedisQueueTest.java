package me.util.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * File Name             :  RedisQueueTest
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
public class RedisQueueTest {

    private final static String QUEUE = "testQueue";

    @Autowired
    private RedisQueue redisQueue;


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void in() throws Exception {

        for (int i = 0; i < 100; i++) {
            redisQueue.in(QUEUE, "value-" + i);
        }

        System.out.println("put data in queue success");
    }

    @Test
    public void out() throws Exception {
        for (int i = 0; i < 100; i++) {
            String value = redisQueue.out(QUEUE);
            System.out.println("get data from queue:" + value);
        }
    }

}