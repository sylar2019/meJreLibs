package me.java.library.io.store.mqtt.common;

/**
 * File Name             :  MqttQoS
 *
 * @author :  sylar
 * Create                :  2020/7/23
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

public enum MqttQoS {
    AT_MOST_ONCE(0),
    AT_LEAST_ONCE(1),
    EXACTLY_ONCE(2),
    FAILURE(0x80);

    private final int value;

    MqttQoS(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static MqttQoS valueOf(int value) {
        for (MqttQoS q: values()) {
            if (q.value == value) {
                return q;
            }
        }
        throw new IllegalArgumentException("invalid QoS: " + value);
    }
}
