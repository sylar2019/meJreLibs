package me.java.library.mq.ons;

import com.google.common.base.Preconditions;
import me.java.library.mq.base.AbstractProducer;
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
public abstract class AbstractOnsProducer extends AbstractProducer {

    public AbstractOnsProducer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    @Override
    public void checkParameters() {
        super.checkParameters();
        Preconditions.checkNotNull(mqProperties.getAccessKey(), "accessKey is null");
        Preconditions.checkNotNull(mqProperties.getSecretKey(), "secretKey is null");
    }
}
