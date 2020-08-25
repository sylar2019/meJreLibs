package me.java.library.mq.ons.mqtt;

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
 * File Name             :  OnsMqttProducerTest
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
public class OnsMqttProducerTest {
    Factory factory;
    Producer producer;
    OnsConst onsConst;

    @Before
    public void setUp() throws Exception {
        OnsProperties op = new OnsProperties(MqProperties.PROVIDER_ONS_TCP, OnsServerConst.MQTT_TEST);
        factory = new OnsMqttFactory(op);
        producer = factory.createProducer(onsConst.getProducerId(), MqttConst.ClientId1);

        producer.start();
    }

    @After
    public void tearDown() throws Exception {
        producer.stop();
    }

    @Test
    public void send() throws Exception {

        /**
         * P2P
         * 如果发送P2P消息，二级Topic必须是“p2p”，三级Topic是目标的ClientID
         * 此处设置的三级Topic需要是接收方的ClientID
         * <p>
         * notice
         * 消息发送到某个主题Topic，所有订阅这个Topic的设备都能收到这个消息。
         * 遵循MQTT的发布订阅规范，Topic也可以是多级Topic。此处设置了发送到二级Topic
         */


        Message msg = new Message(onsConst.getTopic(), "this is a test message");

        //如果 targetClientId != null, 则相当于点对点发送，否则相当于广播发送
        msg.setTargetClientId(MqttConst.ClientId2);

        producer.send(msg);
//        System.out.println("send success. msgId:" + result.getMessageId());
    }

}