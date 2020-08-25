package me.java.library.mq.kafka;

import me.java.library.mq.base.AbstractConsumer;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.kafka.loop.PullLoop;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

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

    public KafkaConsumer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    @Override
    protected void onSubscribe(String topic, MessageListener messageListener, String... tags) throws Exception {
        initConsumer();
        consumer.subscribe(Collections.singleton(topic));
        loop = new PullLoop(consumer, messageListener);
        loop.start();
    }

    @Override
    protected void onUnsubscribe() throws Exception {
        loop.stop();
        loop = null;

        consumer.unsubscribe();
        consumer.close();
        consumer = null;
    }

    private void initConsumer() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, mqProperties.getBrokers());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);

        /* 是否自动确认offset, 强制由业务层来确认 */
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties);
    }
}
