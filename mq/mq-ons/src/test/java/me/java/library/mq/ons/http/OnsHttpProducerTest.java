package me.java.library.mq.ons.http;

import me.java.library.mq.base.Factory;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.base.Producer;
import me.java.library.mq.ons.OnsConst;
import me.java.library.mq.ons.OnsProperties;
import me.java.library.mq.ons.OnsServerConst;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * File Name             :  OnsHttpProducerTest
 * Author                :  sylar
 * Create Date           :  2018/4/14
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
public class OnsHttpProducerTest {
    Factory factory;
    Producer producer;
    OnsConst onsConst;

    @Before
    public void setUp() throws Exception {
        OnsProperties op = new OnsProperties(MqProperties.PROVIDER_ONS_TCP, OnsServerConst.HTTP_TEST);
        factory = new OnsHttpFactory(op);
        producer = factory.createProducer(onsConst.getProducerId(), "ProducerClient_1");

        producer.start();
    }

    @After
    public void tearDown() throws Exception {
        producer.stop();
    }

    @Test
    public void send() throws Exception {
        Message msg = new Message(onsConst.getTopic(), "this is a test message");
        HttpResult result = (HttpResult) producer.send(msg);
        System.out.println("send success. statusCode:" + result.getStatusCode());
    }

}