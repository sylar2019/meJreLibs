package me.java.library.mq.local;


import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import me.java.library.mq.base.AbstractConsumer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.base.MqProperties;
import me.java.library.utils.base.guava.AsyncEventUtils;

import java.util.Optional;

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
public class LocalConsumer extends AbstractConsumer {

    GuavaListener listener;

    public LocalConsumer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    @Override
    protected void onSubscribe(String topic, MessageListener messageListener, String... tags) throws Exception {
        listener = new GuavaListener(topic, messageListener);
        AsyncEventUtils.regist(listener);
    }

    @Override
    protected void onUnsubscribe() throws Exception {
        AsyncEventUtils.unregist(listener);
    }

    static class GuavaListener {
        String topic;
        MessageListener messageListener;

        public GuavaListener(String topic, MessageListener messageListener) {
            this.topic = topic;
            this.messageListener = messageListener;
        }

        @Subscribe
        @AllowConcurrentEvents
        public void onEvent(Message message) {
            Optional.ofNullable(messageListener).ifPresent(l -> l.onSuccess(message));
        }
    }
}
