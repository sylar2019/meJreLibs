package me.java.library.mq.kafka;

import me.java.library.mq.base.AbstractProducer;
import me.java.library.mq.base.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

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
public class KafkaProducer extends AbstractProducer {

    protected org.apache.kafka.clients.producer.KafkaProducer<String, String> producer;

    @Override
    public Object getNativeProducer() {
        return producer;
    }

    @Override
    protected void onStart() throws Exception {
        this.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        //kafka producer 没有group概念，可省略
        this.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        this.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);

        this.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        this.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(this);
    }

    @Override
    protected void onStop() {
        if (producer != null) {
            producer.close();
            producer = null;
        }
    }

    @Override
    public Object send(Message message) throws Exception {
        checkOnSend(message);
        return producer.send(new ProducerRecord<>(message.getTopic(), message.getContent()));
    }

}
