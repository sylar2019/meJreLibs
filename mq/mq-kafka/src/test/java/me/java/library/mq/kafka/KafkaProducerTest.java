package me.java.library.mq.kafka;

import me.java.library.mq.base.Factory;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.base.Producer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Future;

/**
 * File Name             :  KafkaProducerTest
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
public class KafkaProducerTest {

    Factory factory;
    Producer producer;

    String brokers = "localhost:9092";
    String topic = "TopicA";

    @Before
    public void setUp() throws Exception {
        MqProperties mqProperties = new MqProperties() {
            @Override
            public String getProvider() {
                return MqProperties.PROVIDER_KAFKA;
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
        factory = new KafkaFactory(mqProperties);
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
        Future<RecordMetadata> result = (Future<RecordMetadata>) producer.send(msg);
        System.out.println("send success. offset:" + result.get().offset());
    }

}