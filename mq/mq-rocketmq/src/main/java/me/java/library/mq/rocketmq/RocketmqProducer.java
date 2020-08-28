package me.java.library.mq.rocketmq;

import com.google.common.base.Charsets;
import me.java.library.mq.base.AbstractProducer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
import org.apache.rocketmq.client.producer.DefaultMQProducer;


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
public class RocketmqProducer extends AbstractProducer {

    protected DefaultMQProducer producer;

    public RocketmqProducer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    @Override
    protected void onStart() throws Exception {
        producer = new DefaultMQProducer();
        producer.setNamesrvAddr(mqProperties.getBrokers());
        producer.setProducerGroup(groupId);
        producer.setInstanceName(clientId);
        producer.setVipChannelEnabled(false);
        producer.start();
    }

    @Override
    protected void onStop() {
        if (producer != null) {
            producer.shutdown();
            producer = null;
        }
    }

    @Override
    protected Object onSend(Message message) throws Exception {
        return producer.send(new org.apache.rocketmq.common.message.Message(
                message.getTopic(),
                message.getTag(),
                message.getKey(),
                message.getContent().getBytes(Charsets.UTF_8)));
    }

}
