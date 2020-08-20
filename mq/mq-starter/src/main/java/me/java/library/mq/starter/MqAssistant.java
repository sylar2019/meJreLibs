package me.java.library.mq.starter;

import me.java.library.mq.base.Consumer;
import me.java.library.mq.base.Factory;
import me.java.library.mq.base.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * File Name             :  MqAssistant
 *
 * @author :  sylar
 * Create Date           :  2018/4/18
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
@Component
public class MqAssistant {

    @Autowired
    private MqProperties mqProperties;

    @Autowired
    private Factory factory;

    public Producer createProducer(String groupId, String clientId) {
        return factory.createProducer(mqProperties.getBrokers(), groupId, clientId);
    }

    public Consumer createConsumer(String groupId, String clientId) {
        return factory.createConsumer(mqProperties.getBrokers(), groupId, clientId);
    }

}
