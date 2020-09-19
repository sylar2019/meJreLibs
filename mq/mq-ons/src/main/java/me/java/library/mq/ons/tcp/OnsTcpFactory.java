package me.java.library.mq.ons.tcp;

import me.java.library.mq.base.AbstractFactory;
import me.java.library.mq.base.Consumer;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.base.Producer;

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
public class OnsTcpFactory extends AbstractFactory {

    public OnsTcpFactory(MqProperties mqProperties) {
        super(mqProperties);
    }

    @Override
    public Producer createProducer(String producerGroupId, String clientId) {
        return new OnsTcpProducer(mqProperties, producerGroupId, clientId);
    }

    @Override
    public Consumer createConsumer(String consumerGroupId, String clientId) {
        return new OnsTcpConsumer(mqProperties, consumerGroupId, clientId);
    }

}
