package me.java.library.utils.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * File Name             :  RedisCacheTest
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
public class RedisOperationsTest {

    private final static String KEY = "testKey";

    @Autowired
    private RedisOperations redisOperations;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void opsValue() throws Exception {
        redisOperations.opsValue(KEY).set("testValue");
        System.out.println("set value success");
        System.out.println("get value :" + redisOperations.opsValue(KEY).get());
    }

    @Test
    public void opsMap() throws Exception {
    }

    @Test
    public void opsList() throws Exception {
    }

    @Test
    public void opsSet() throws Exception {
    }

    @Test
    public void opsZSet() throws Exception {
    }

    @Test
    public void opsGeo() throws Exception {
    }

}