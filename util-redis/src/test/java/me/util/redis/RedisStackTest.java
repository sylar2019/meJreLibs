package me.util.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * File Name             :  RedisStackTest
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
public class RedisStackTest {

    private final static String STACK = "testStack";

    @Autowired
    private RedisStack redisStack;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void push() throws Exception {
        for (int i = 0; i < 100; i++) {
            redisStack.push(STACK, "value-" + i);
        }

        System.out.println("push data in stack success");
    }

    @Test
    public void pop() throws Exception {
        for (int i = 0; i < 100; i++) {
            String value = redisStack.pop(STACK);
            System.out.println("pop data from stack:" + value);
        }
    }

}