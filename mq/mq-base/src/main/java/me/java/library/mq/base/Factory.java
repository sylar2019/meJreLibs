package me.java.library.mq.base;

import java.util.UUID;

/**
 * @author :  sylar
 * @FileName :  Factory
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
public interface Factory {

    /**
     * MQ配置参数
     *
     * @return
     */
    MqProperties getMqProperties();

    /**
     * 创建生产者,使用默认生产组
     *
     * @return
     */
    default Producer createProducer() {
        return createProducer(MqProperties.DEFAULT_PRODUCER_GROUP, UUID.randomUUID().toString());
    }

    /**
     * 创建生产者
     *
     * @param producerGroupId client端所属groups标识
     * @param clientId        client标识
     * @return MQ消息生产者
     */
    Producer createProducer(String producerGroupId, String clientId);

    /**
     * 创建消费者,使用默认消费组
     *
     * @return
     */
    default Consumer createConsumer() {
        return createConsumer(MqProperties.DEFAULT_CONSUMER_GROUP, UUID.randomUUID().toString());
    }

    /**
     * 创建消费者
     *
     * @param consumerGroupId client端所属groups标识
     * @param clientId        client标识
     * @return MQ消息消费者
     */
    Consumer createConsumer(String consumerGroupId, String clientId);
}
