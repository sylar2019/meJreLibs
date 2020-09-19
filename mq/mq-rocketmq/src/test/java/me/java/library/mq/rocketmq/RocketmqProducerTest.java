package me.java.library.mq.rocketmq;

import me.java.library.mq.base.Factory;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.base.Producer;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * File Name             :  RocketmqProducerTest
 * Author                :  sylar
 * Create Date           :  2018/4/12
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
public class RocketmqProducerTest {

    Factory factory;
    Producer producer;

    String brokers = "localhost:9876";
    String topic = "TopicA";

    //sh mqadmin updateTopic -t TopicA -c DefaultCluster -n rocket_host:9876

    @Before
    public void setUp() throws Exception {
        MqProperties mqProperties = new MqProperties() {
            @Override
            public String getProvider() {
                return MqProperties.PROVIDER_ROCKETMQ;
            }

            @Override
            public String getBrokers() {
                return brokers;
            }

            @Override
            public String getUser() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getAccessKey() {
                return null;
            }

            @Override
            public String getSecretKey() {
                return null;
            }

            @Override
            public <T> T getAttr(String attrKey) {
                return null;
            }
        };

        factory = new RocketmqFactory(mqProperties);
        producer = factory.createProducer("ProducerGroup_1", "ProducerClient_1");
        producer.start();
    }

    @After
    public void tearDown() throws Exception {
        producer.stop();
    }

    @Test
    public void send() throws Exception {
        Message msg = new Message(topic, "this is a test message");
        SendResult result = (SendResult) producer.send(msg);
        System.out.println("send success. msgId:" + result.getMsgId());
    }

}