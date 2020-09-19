package me.java.library.mq.starter;

import me.java.library.mq.base.MqProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author : sylar
 * @fullName : me.java.library.mq.starter.DefaultMqProperties
 * @createDate : 2020/8/24
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
@ConfigurationProperties(prefix = DefaultMqProperties.PREFIX_MQ)
public class DefaultMqProperties implements MqProperties {
    final public static String PREFIX_MQ = "mq";

    /**
     * MQ服务器供应商【必填项】
     */
    private String provider;
    /**
     * MQ服务器地址【必填项】
     */
    private String brokers;
    /**
     * MQ服务器的用户名【可选项】
     */
    private String user;
    /**
     * MQ服务器的密码【可选项】
     */
    private String password;
    /**
     * MQ服务器的 accessKey【可选项，通常是应用在公有云环境】
     */
    private String accessKey;
    /**
     * MQ服务器的 secretKey【可选项，通常是应用在公有云环境】
     */
    private String secretKey;
    /**
     * 其它附加属性
     */
    private Map<String, Object> attrs;

    @Override
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String getBrokers() {
        return brokers;
    }

    public void setBrokers(String brokers) {
        this.brokers = brokers;
    }

    @Override
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    @Override
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAttr(String attrKey) {
        assert attrKey != null;
        if (attrs != null && attrs.containsKey(attrKey)) {
            return (T) attrs.get(attrKey);
        }
        return null;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }
}
