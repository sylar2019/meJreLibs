package me.java.library.mq.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.java.library.utils.base.JsonUtils;

/**
 * @author :  sylar
 * @FileName :  Message
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
public class Message {
    private String topic;
    private String content;
    private String tags;
    private String keys;

    /**
     * 当使用mqtt p2p 方式 发送MQ消息时，需要对端的clientId
     * <p>
     * 如果发送P2P消息，二级Topic必须是“p2p”，三级Topic是目标的ClientID
     * 此处设置的三级Topic需要是接收方的ClientID
     * <p>
     * notice
     * 消息发送到某个主题Topic，所有订阅这个Topic的设备都能收到这个消息。
     * 遵循MQTT的发布订阅规范，Topic也可以是多级Topic。此处设置了发送到二级Topic
     */
    private String targetClientId;
    private Object ext;

    public Message(String topic) {
        this.topic = topic;
    }

    public Message(String topic, String content) {
        this.topic = topic;
        this.content = content;
    }

    public Message(String topic, String content, String tags, String keys) {
        this.topic = topic;
        this.content = content;
        this.tags = tags;
        this.keys = keys;
    }

    @Override
    public synchronized String toString() {
        return JsonUtils.toJSONString(this);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonIgnore
    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getTargetClientId() {
        return targetClientId;
    }

    public void setTargetClientId(String targetClientId) {
        this.targetClientId = targetClientId;
    }
}
