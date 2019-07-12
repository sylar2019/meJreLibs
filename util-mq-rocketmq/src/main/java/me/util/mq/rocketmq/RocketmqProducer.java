package me.util.mq.rocketmq;

import com.google.common.base.Charsets;
import me.util.mq.AbstractProducer;
import me.util.mq.Message;
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

    @Override
    public Object getNativeProducer() {
        return producer;
    }

    @Override
    protected void onStart() throws Exception {
        producer = new DefaultMQProducer();
        producer.setNamesrvAddr(brokers);
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
    public Object send(Message message) throws Exception {
        checkOnSend(message);
        return producer.send(new org.apache.rocketmq.common.message.Message(
                message.getTopic(),
                message.getTags(),
                message.getKeys(),
                message.getContent()
                        .getBytes(Charsets.UTF_8)));
    }

}
