package me.java.library.mq.redis;

import me.java.library.mq.base.AbstractFactory;
import me.java.library.mq.base.Consumer;
import me.java.library.mq.base.Producer;

/**
 * @author :  sylar
 * @FileName :  KafkaFactory
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
public class RedisFactory extends AbstractFactory {

    @Override
    public Producer createProducer(String brokers, String groupId, String clientId) {
        RedisProducer producer = new RedisProducer();
        setClient(producer, brokers, groupId, clientId);
        return producer;
    }

    @Override
    public Consumer createConsumer(String brokers, String groupId, String clientId) {
        RedisConsumer consumer = new RedisConsumer();
        setClient(consumer, brokers, groupId, clientId);
        return consumer;
    }
}
