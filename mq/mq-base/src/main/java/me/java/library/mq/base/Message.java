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
    private String tag;
    private String key;
    private Object ext;

    public Message(String topic) {
        this.topic = topic;
    }

    public Message(String topic, String content) {
        this.topic = topic;
        this.content = content;
    }

    public Message(String topic, String content, String tag, String key) {
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

    @JsonIgnore
    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
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
