package me.java.library.io.store.mqtt.client;

import me.java.library.io.base.pipe.BasePipeParams;

import java.util.UUID;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.mqtt.client.MqttClientParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class MqttClientParams extends BasePipeParams {

    /**
     * uri of mqtt server
     * eg: {@code tcp://127.0.0.1:1883}
     * eg: {@code ssl://127.0.0.1:1884}
     */
    String broker;
    String clientId;
    String username;
    String password;

    public MqttClientParams(String broker) {
        this(broker, UUID.randomUUID().toString());
    }

    public MqttClientParams(String broker, String clientId) {
        this.broker = broker;
        this.clientId = clientId;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
