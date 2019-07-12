package me.util.mq.rocketmq;

import me.util.mq.AbstractFactory;
import me.util.mq.IConsumer;
import me.util.mq.IProducer;

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
public class RocketmqFactory extends AbstractFactory {

    @Override
    public IProducer createProducer(String brokers, String groupId, String clientId) {
        RocketmqProducer producer = new RocketmqProducer();
        setClient(producer, brokers, groupId, clientId);
        return producer;
    }

    @Override
    public IConsumer createConsumer(String brokers, String groupId, String clientId) {
        RocketmqConsumer consumer = new RocketmqConsumer();
        setClient(consumer, brokers, groupId, clientId);
        return consumer;
    }
}
