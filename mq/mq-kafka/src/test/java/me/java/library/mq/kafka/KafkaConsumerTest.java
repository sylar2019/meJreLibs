package me.java.library.mq.kafka;

import me.java.library.mq.base.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * File Name             :  KafkaConsumerTest
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
public class KafkaConsumerTest {
    Factory factory;
    Consumer consumer;

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
        consumer = factory.createConsumer("ConsumerGroup_1", "ConsumerClient_1");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void subscribe() throws Exception {
        consumer.subscribe(topic, new MessageListener() {
            @Override
            public void onSuccess(Message message) {
                System.out.println(String.format("[TOPIC]:%s [MESSAGE]:%s", message.getTopic(), message.getContent()));

                if (message.getExt() instanceof ConsumerRecord) {
                    ConsumerRecord ext = (ConsumerRecord) message.getExt();
                    System.out.println("offset:" + ext.offset());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        Thread.sleep(1000 * 60 * 5);
        System.out.println("test over");
    }

    @Test
    public void unsubscribe() throws Exception {
        consumer.unsubscribe();
    }

}