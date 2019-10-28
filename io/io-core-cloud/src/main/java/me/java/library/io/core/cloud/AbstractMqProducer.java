package me.java.library.io.core.cloud;

import me.java.library.io.core.cloud.msg.MqMsg;
import me.java.library.mq.base.Producer;
import me.java.library.mq.base.Message;

/**
 * File Name             :  AbstractMqProducer
 *
 * @Author :  sylar
 * Create                :  2019-10-23
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AbstractMqProducer extends AbstractMqClient {
    protected Producer producer;

    protected abstract String getTopic();

    public AbstractMqProducer(String brokers) {
        this.producer = factory.createProducer(brokers, getGroupId(), getClientId());
    }

    @Override
    public void dispose() {
        producer.stop();
    }

    protected void send(MqMsg msg) {
        try {
            Message message = new Message(getTopic(), msg.toString());
            producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
