package me.java.library.io.store.mqtt;

import me.java.library.io.store.mqtt.client.MqttClientOptions;
import me.java.library.io.store.mqtt.client.MqttClientPipe;

/**
 * File Name             :  MqttExpress
 *
 * @author :  sylar
 * Create                :  2020/7/22
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
public class MqttExpress {
    public static MqttClientPipe client(MqttClientOptions options) {
        return new MqttClientPipe(options);
    }
}
