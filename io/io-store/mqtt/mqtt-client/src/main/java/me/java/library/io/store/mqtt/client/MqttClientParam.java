package me.java.library.io.store.mqtt.client;

import java.util.UUID;

/**
 * File Name             :  MqttClientConfig
 *
 * @author :  sylar
 * Create                :  2020/7/20
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
public class MqttClientParam {

    /**
     * uri of mqtt server
     * eg: {@code tcp://ip:port}
     */
    String broker;
    String clientId;
    String username;
    String password;

    public MqttClientParam(String broker) {
        this(broker, UUID.randomUUID().toString());
    }

    public MqttClientParam(String broker, String clientId) {
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
