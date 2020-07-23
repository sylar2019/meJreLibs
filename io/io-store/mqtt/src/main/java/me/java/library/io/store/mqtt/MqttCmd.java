package me.java.library.io.store.mqtt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.java.library.io.common.cmd.CmdNode;

/**
 * File Name             :  MqttCmdNode
 *
 * @author :  sylar
 * Create                :  2020/7/14
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
public class MqttCmd extends CmdNode {
    private final static String ATTR_TOPIC = "mqttTopic";
    private final static String ATTR_CONTENT = "mqttContent";

    public MqttCmd(String topic, String content) {
        setTopic(topic);
        setContent(content);
    }

    @JsonIgnore
    public String getTopic() {
        return getAttr(ATTR_TOPIC);
    }

    public void setTopic(String topic) {
        setAttr(ATTR_TOPIC, topic);
    }

    @JsonIgnore
    public String getContent() {
        return getAttr(ATTR_CONTENT);
    }

    public void setContent(String content) {
        setAttr(ATTR_CONTENT, content);
    }
}
