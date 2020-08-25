package me.java.library.mq.ons;

import me.java.library.mq.base.MqProperties;

/**
 * @author : sylar
 * @fullName : me.java.library.mq.ons.OnsProperties
 * @createDate : 2020/8/25
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class OnsProperties implements MqProperties {

    String provider;
    String brokers;
    OnsConst onsConst = OnsConst.getFirst();

    public OnsProperties(String provider, String brokers) {
        this.provider = provider;
        this.brokers = brokers;
    }

    @Override
    public String getProvider() {
        return provider;
    }

    @Override
    public String getBrokers() {
        return brokers;
    }

    @Override
    public String getUser() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getAccessKey() {
        return onsConst.getAccessKey();
    }

    @Override
    public String getSecretKey() {
        return onsConst.getSecretKey();
    }

    @Override
    public <T> T getAttr(String attrKey) {
        return null;
    }
}
