package me.java.library.io.mq;

import me.java.library.mq.base.Consumer;

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
public abstract class AbstractMqConsumer extends AbstractMqClient {
    protected Consumer consumer;

    public AbstractMqConsumer() {
        this.consumer = factory.createConsumer(getGroupId(), getClientId());
    }

    @Override
    protected String getGroupId() {
        return "default_consumer_group";
    }

    @Override
    public void dispose() {
        consumer.unsubscribe();
    }
}
