package me.java.library.mq.kafka;

import me.java.library.mq.base.AbstractProducer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

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
public class KafkaProducer extends AbstractProducer {

    protected org.apache.kafka.clients.producer.KafkaProducer<String, String> producer;

    public KafkaProducer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }


    @Override
    protected void onStart() throws Exception {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, mqProperties.getBrokers());
        //kafka producer 没有group概念，可省略
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
    }

    @Override
    protected void onStop() throws Exception {
        producer.close();
        producer = null;
    }

    @Override
    protected Object onSend(Message message) throws Exception {
        return producer.send(new ProducerRecord<>(message.getTopic(), message.getKeys(), message.getContent()));
    }

}
