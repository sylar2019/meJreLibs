package me.java.library.io.core.cloud;

import me.java.library.mq.base.IConsumer;

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
public abstract class AbstractMqConsumer extends AbstractMqClient {
    protected IConsumer consumer;

    public AbstractMqConsumer(String brokers) {
        this.consumer = factory.createConsumer(brokers, getGroupId(), getClientId());
    }

    @Override
    public void dispose() {
        consumer.unsubscribe();
    }
}
