package me.java.library.mq.kafka;

import me.java.library.mq.base.IFactory;
import me.java.library.mq.base.IProducer;
import me.java.library.mq.base.Message;
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

    IFactory factory;
    IProducer producer;

    String brokers = "localhost:9092";
    String topic = "TopicA";

    @Before
    public void setUp() throws Exception {
        factory = new KafkaFactory();
        producer = factory.createProducer(brokers, "ProducerGroup_1", "ProducerClient_1");

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