package me.java.library.mq.base;

/**
 * @author : sylar
 * @fullName : me.java.library.mq.base.FactoryProperties
 * @createDate : 2020/8/24
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public interface MqProperties {
    String DEFAULT_PRODUCER_GROUP = "default_producer_group";
    String DEFAULT_CONSUMER_GROUP = "default_consumer_group";

    String PROVIDER_ROCKETMQ = "rocketmq";
    String PROVIDER_RABBITMQ = "rabbitmq";
    String PROVIDER_KAFKA = "kafka";
    String PROVIDER_ONS_TCP = "ons.tcp";
    String PROVIDER_ONS_HTTP = "ons.http";
    String PROVIDER_ONS_MQTT = "ons.mqtt";
    String PROVIDER_REDIS = "redis";
    String PROVIDER_LOCAL = "local";

    String getProvider();

    String getBrokers();

    String getUser();

    String getPassword();

    String getAccessKey();

    String getSecretKey();

    <T> T getAttr(String attrKey);

    default <T> T getAttrOrDefault(String attrKey, T defalutValue) {
        T v = getAttr(attrKey);
        return v != null ? v : defalutValue;
    }
}
