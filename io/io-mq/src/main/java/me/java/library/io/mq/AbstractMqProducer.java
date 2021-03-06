package me.java.library.io.mq;

import me.java.library.io.mq.msg.MqMsg;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.Producer;

/**
 * File Name             :  AbstractMqProducer
 *
 * @author :  sylar
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

    public AbstractMqProducer() {
        this.producer = factory.createProducer(getGroupId(), getClientId());
        this.producer.start();
    }

    protected abstract String getTopic();

    @Override
    protected String getGroupId() {
        return "default_producer_group";
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
