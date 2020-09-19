package me.java.library.mq.redis;


import me.java.library.mq.base.AbstractConsumer;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.base.MqProperties;
import me.java.library.utils.redis.RedisNotice;
import me.java.library.utils.spring.SpringBeanUtils;
import org.springframework.data.redis.connection.Message;

import java.util.Collections;

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

    RedisNotice redisNotice;
    RedisListener listener;

    public RedisConsumer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
        redisNotice = SpringBeanUtils.getBean(RedisNotice.class);
    }

    @Override
    protected void onSubscribe(String topic, MessageListener messageListener, String... tags) throws Exception {
        listener = new RedisListener(topic, messageListener);
        redisNotice.subscribe(listener, Collections.singletonList(topic));
    }

    @Override
    protected void onUnsubscribe() throws Exception {
        redisNotice.unsubscribe(listener, Collections.singletonList(topic));
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
