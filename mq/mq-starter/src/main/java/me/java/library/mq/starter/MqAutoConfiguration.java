package me.java.library.mq.starter;

import me.java.library.mq.base.Factory;
import me.java.library.mq.kafka.KafkaFactory;
import me.java.library.mq.ons.http.OnsHttpFactory;
import me.java.library.mq.ons.mqtt.OnsMqttFactory;
import me.java.library.mq.ons.tcp.OnsTcpFactory;
import me.java.library.mq.rocketmq.RocketmqFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * File Name             :  MqAutoConfiguration
 * Author                :  sylar
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
@Configuration
@EnableConfigurationProperties(MqProperties.class)
public class MqAutoConfiguration {

    @Autowired
    private MqProperties mqProperties;

    @Bean
    @ConditionalOnMissingBean(Factory.class)
    public Factory getFactory() {
        switch (mqProperties.getProvider()) {
            case MqProperties.PROVIDER_ROCKETMQ:
                return new RocketmqFactory();
            case MqProperties.PROVIDER_KAFKA:
                return new KafkaFactory();
            case MqProperties.PROVIDER_ONS_TCP:
                return new OnsTcpFactory(mqProperties.getAccessKey(), mqProperties.getSecretKey());
            case MqProperties.PROVIDER_ONS_HTTP:
                return new OnsHttpFactory(mqProperties.getAccessKey(), mqProperties.getSecretKey());
            case MqProperties.PROVIDER_ONS_MQTT:
                return new OnsMqttFactory(mqProperties.getAccessKey(), mqProperties.getSecretKey());
            default:
                return new RocketmqFactory();
        }
    }
}
