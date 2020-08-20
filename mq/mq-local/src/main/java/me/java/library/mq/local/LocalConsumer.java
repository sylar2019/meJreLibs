package me.java.library.mq.local;


import com.google.common.collect.Maps;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import me.java.library.mq.base.AbstractConsumer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import me.java.library.utils.base.guava.AsyncEventUtils;

import java.util.Map;
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

    private final Map<String, GuavaListener> listeners = Maps.newHashMap();

    @Override
    public Object getNativeConsumer() {
        return null;
    }

    @Override
    public void subscribe(String topic, MessageListener messageListener, String... tags) {
        super.subscribe(topic, messageListener, tags);
        if (topic == null || messageListener == null || listeners.containsKey(topic)) {
            return;
        }

        GuavaListener listener = new GuavaListener(topic, messageListener);
        listeners.put(topic, listener);
        AsyncEventUtils.regist(listener);
    }

    @Override
    public void unsubscribe() {
        listeners.values().forEach(AsyncEventUtils::unregist);
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
