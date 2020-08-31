package me.java.library.mq.base;

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
    private String tag;
    private String key;

    public Message(String topic) {
        this.topic = topic;
    }

    public Message(String topic, String content) {
        this(topic, content, null, null);
    }

    public Message(String topic, String content, String tag, String key) {
        assert topic != null;
        assert content != null;

        this.topic = topic;
        this.content = content;
        this.tag = tag;
        this.key = key;
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


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
