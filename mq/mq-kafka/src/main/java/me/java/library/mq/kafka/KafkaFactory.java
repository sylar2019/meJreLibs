package me.java.library.mq.kafka;

import me.java.library.mq.base.AbstractFactory;
import me.java.library.mq.base.Consumer;
import me.java.library.mq.base.MqProperties;
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
public class KafkaFactory extends AbstractFactory {
    public KafkaFactory(MqProperties mqProperties) {
        super(mqProperties);
    }

    @Override
    public Producer createProducer(String producerGroupId, String clientId) {
        return new KafkaProducer(mqProperties, producerGroupId, clientId);
    }

    @Override
    public Consumer createConsumer(String consumerGroupId, String clientId) {
        return new KafkaConsumer(mqProperties, consumerGroupId, clientId);
    }

}
