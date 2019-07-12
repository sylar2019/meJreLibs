package me.util.mq.ons.tcp;

import me.util.mq.IConsumer;
import me.util.mq.IProducer;
import me.util.mq.ons.AbstractOnsFactory;

/**
 * @author :  sylar
 * @FileName :  OnsTcpFactory
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
public class OnsTcpFactory extends AbstractOnsFactory {

    public OnsTcpFactory(String accessKey, String secretKey) {
        super(accessKey, secretKey);
    }

    @Override
    public IProducer createProducer(String brokers, String groupId, String clientId) {
        OnsTcpProducer producer = new OnsTcpProducer();
        setClient(producer, brokers, groupId, clientId);
        setProducerKey(producer, accessKey, secretKey);
        return producer;
    }

    @Override
    public IConsumer createConsumer(String brokers, String groupId, String clientId) {
        OnsTcpConsumer consumer = new OnsTcpConsumer();
        setClient(consumer, brokers, groupId, clientId);
        setConsumerKey(consumer, accessKey, secretKey);
        return consumer;
    }
}
