package me.java.library.mq.ons.mqtt;

import me.java.library.mq.base.IConsumer;
import me.java.library.mq.base.IProducer;
import me.java.library.mq.ons.AbstractOnsFactory;

/**
 * @author :  sylar
 * @FileName :  OnsMqttFactory
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
public class OnsMqttFactory extends AbstractOnsFactory {

    public OnsMqttFactory(String accessKey, String secretKey) {
        super(accessKey, secretKey);
    }

    @Override
    public IProducer createProducer(String brokers, String groupId, String clientId) {
        OnsMqttProducer producer = new OnsMqttProducer();
        setClient(producer, brokers, groupId, clientId);
        setProducerKey(producer, accessKey, secretKey);
        return producer;
    }

    @Override
    public IConsumer createConsumer(String brokers, String groupId, String clientId) {
        OnsMqttConsumer consumer = new OnsMqttConsumer();
        setClient(consumer, brokers, groupId, clientId);
        setConsumerKey(consumer, accessKey, secretKey);
        return consumer;
    }
}