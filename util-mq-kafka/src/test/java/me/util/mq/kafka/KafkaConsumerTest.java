package me.util.mq.kafka;

import me.util.mq.IConsumer;
import me.util.mq.IFactory;
import me.util.mq.Message;
import me.util.mq.MessageListener;
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
    IFactory factory;
    IConsumer consumer;

    String brokers = "localhost:9092";
    String topic = "TopicA";

    @Before
    public void setUp() throws Exception {
        factory = new KafkaFactory();
        consumer = factory.createConsumer(brokers, "ConsumerGroup_1", "ConsumerClient_1");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void subscribe() throws Exception {
        consumer.subscribe(topic, null, new MessageListener() {
            @Override
            public void onSuccess(Message message) {
                System.out.println(String.format("[TOPIC]:%s [MESSAGE]:%s", message.getTopic(), message.getContent()));

                if (message.getExt() instanceof ConsumerRecord) {
                    ConsumerRecord<String, String> ext = (ConsumerRecord<String, String>) message.getExt();
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