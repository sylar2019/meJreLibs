package me.java.library.mq.ons.tcp;

import com.aliyun.openservices.ons.api.SendResult;
import me.java.library.mq.base.Factory;
import me.java.library.mq.base.Producer;
import me.java.library.mq.base.Message;
import me.java.library.mq.ons.OnsConst;
import me.java.library.mq.ons.OnsServerConst;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * File Name             :  OnsTcpProducerTest
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
public class OnsTcpProducerTest {

    Factory factory;
    Producer producer;
    OnsConst onsConst;

    @Before
    public void setUp() throws Exception {
        onsConst = OnsConst.getFirst();
        factory = new OnsTcpFactory(onsConst.getAccessKey(), onsConst.getSecretKey());
        producer = factory.createProducer(OnsServerConst.TCP_TEST, onsConst.getProducerId(), "ProducerClient_1");

        producer.start();
    }

    @After
    public void tearDown() throws Exception {
        producer.stop();
    }

    @Test
    public void send() throws Exception {
        Message msg = new Message(onsConst.getTopic(), "this is a test message");
        SendResult result = (SendResult) producer.send(msg);
        System.out.println("send success. msgId:" + result.getMessageId());
    }

}