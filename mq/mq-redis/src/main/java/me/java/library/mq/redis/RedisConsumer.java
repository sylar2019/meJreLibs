package me.java.library.mq.redis;


import com.google.common.collect.Maps;
import me.java.library.mq.base.AbstractConsumer;
import me.java.library.mq.base.MessageListener;
import me.java.library.utils.redis.RedisNotice;
import me.java.library.utils.spring.SpringBeanUtils;
import org.springframework.data.redis.connection.Message;

import java.util.Collections;
import java.util.Map;

/**
 * @author :  sylar
 * @FileName :  RocketmqConsumer
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
public class RedisConsumer extends AbstractConsumer {

    private final RedisNotice redisNotice;
    private final Map<String, RedisListener> listeners = Maps.newHashMap();

    public RedisConsumer() {
        redisNotice = SpringBeanUtils.getBean(RedisNotice.class);
    }

    @Override
    public Object getNativeConsumer() {
        return redisNotice;
    }

    @Override
    public void subscribe(String topic, MessageListener messageListener, String... tags) {
        super.subscribe(topic, messageListener, tags);
        if (messageListener == null || listeners.containsKey(topic)) {
            return;
        }

        RedisListener listener = new RedisListener(topic, messageListener);
        redisNotice.subscribe(listener, Collections.singletonList(topic));
        listeners.put(topic, listener);
    }

    @Override
    public void unsubscribe() {
        listeners.values().forEach(l -> redisNotice.unsubscribe(l, null));
    }

    static class RedisListener implements org.springframework.data.redis.connection.MessageListener {
        String topic;
        MessageListener listener;

        public RedisListener(String topic, MessageListener listener) {
            this.topic = topic;
            this.listener = listener;
        }

        @Override
        public void onMessage(Message message, byte[] pattern) {
            String content = new String(message.getBody());

            me.java.library.mq.base.Message msg = new me.java.library.mq.base.Message(topic, content);
            listener.onSuccess(msg);
        }
    }
}
