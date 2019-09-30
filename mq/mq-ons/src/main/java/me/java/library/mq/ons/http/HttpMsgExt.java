package me.java.library.mq.ons.http;

/**
 * Created by sylar on 2017/1/5.
 */
public class HttpMsgExt {
    private String body;
    private String msgId;
    private String bornTime;
    private String msgHandle;
    private int reconsumeTimes;
    private String tag;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getBornTime() {
        return bornTime;
    }

    public void setBornTime(String bornTime) {
        this.bornTime = bornTime;
    }

    public String getMsgHandle() {
        return msgHandle;
    }

    public void setMsgHandle(String msgHandle) {
        this.msgHandle = msgHandle;
    }

    public int getReconsumeTimes() {
        return reconsumeTimes;
    }

    public void setReconsumeTimes(int reconsumeTimes) {
        this.reconsumeTimes = reconsumeTimes;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
