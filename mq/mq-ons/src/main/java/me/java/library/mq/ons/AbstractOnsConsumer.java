package me.java.library.mq.ons;

import com.google.common.base.Preconditions;
import me.java.library.mq.base.AbstractConsumer;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.base.MqProperties;

/**
 * File Name             :  AbstractOnsProducer
 * Author                :  sylar
 * Create Date           :  2018/4/11
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
public abstract class AbstractOnsConsumer extends AbstractConsumer {

    public AbstractOnsConsumer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    @Override
    protected void checkOnSubscribe(String topic, MessageListener messageListener) {
        super.checkOnSubscribe(topic, messageListener);
        Preconditions.checkNotNull(mqProperties.getAccessKey(), "accessKey is null");
        Preconditions.checkNotNull(mqProperties.getSecretKey(), "secretKey is null");
    }

}
