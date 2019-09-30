package me.java.library.mq.kafka;

import me.java.library.mq.base.AbstractConsumer;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.kafka.loop.PullLoop;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;

/**
 * @author :  sylar
 * @FileName :
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
public class KafkaConsumer extends AbstractConsumer {

    protected org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer;
    protected PullLoop loop;

    @Override
    public Object getNativeConsumer() {
        return consumer;
    }

    @Override
    public void subscribe(String topic, String[] tags, MessageListener messageListener) {
        super.subscribe(topic, tags, messageListener);

        try {
            initConsumer();
            consumer.subscribe(Collections.singleton(topic));
            loop = new PullLoop(consumer, messageListener);
            loop.start();
        } catch (Exception e) {
            messageListener.onFailure(e);
        }

    }

    @Override
    public void unsubscribe() {
        if (consumer != null) {
            consumer.unsubscribe();
            consumer.close();
            consumer = null;
        }

        if (loop != null) {
            loop.stop();
            loop = null;
        }
    }

    private void initConsumer() {
        this.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        this.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        this.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);

        /* 是否自动确认offset, 强制由业务层来确认 */
        this.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        this.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        this.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(this);
    }
}
