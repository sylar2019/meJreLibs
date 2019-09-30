package me.java.library.mq.ons.http;


import me.java.library.utils.base.JsonUtils;

import java.io.Serializable;

public class HttpResult implements Serializable {

    int statusCode;
    String content;

    public HttpResult(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return JsonUtils.toJSONString(this);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
