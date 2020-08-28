package me.java.library.mq.kafka;

import me.java.library.mq.base.AbstractProducer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
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
        //序列化类型配置
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //服务端地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, mqProperties.getBrokers());
        //客户端ID
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        //重试 0 为不启用重试机制
        properties.put(ProducerConfig.RETRIES_CONFIG, 1);
        //控制批处理大小，单位为字节
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024 * 16);
        //批量发送，延迟为1毫秒，启用该功能能有效减少生产者发送消息次数，从而提高并发量
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);


        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
    }

    @Override
    protected void onStop() throws Exception {
        producer.close();
        producer = null;
    }

    @Override
    protected Object onSend(Message message) throws Exception {
        return producer.send(new ProducerRecord<>(message.getTopic(), message.getKey(), message.getContent()));
    }

}
