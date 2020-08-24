package me.java.library.mq.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * File Name             :  MqProperties
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
@ConfigurationProperties(prefix = MqProperties.PREFIX_MQ)
public class MqProperties {

    final public static String PREFIX_MQ = "mq";
    final public static String PREFIX_PROVIDER = PREFIX_MQ + ".provider";
    final public static String PREFIX_BROKERS = PREFIX_MQ + ".brokers";
    final public static String PREFIX_ACCESS_KEY = PREFIX_MQ + ".accessKey";
    final public static String PREFIX_SERCET_KEY = PREFIX_MQ + ".secretKey";

    final public static String PROVIDER_ROCKETMQ = "rocketmq";
    final public static String PROVIDER_RABBITMQ = "rabbitmq";
    final public static String PROVIDER_KAFKA = "kafka";
    final public static String PROVIDER_ONS_TCP = "ons.tcp";
    final public static String PROVIDER_ONS_HTTP = "ons.http";
    final public static String PROVIDER_ONS_MQTT = "ons.mqtt";
    final public static String PROVIDER_REDIS = "redis";
    final public static String PROVIDER_LOCAL = "local";

    private String provider;
    private String brokers;
    private String accessKey;
    private String secretKey;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getBrokers() {
        return brokers;
    }

    public void setBrokers(String brokers) {
        this.brokers = brokers;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
