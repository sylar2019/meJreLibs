package me.util.mq.rocketmq;


import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import me.util.mq.AbstractConsumer;
import me.util.mq.Message;
import me.util.mq.MessageListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

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
public class RocketmqConsumer extends AbstractConsumer {

    protected DefaultMQPushConsumer consumer;
    private String topic = null;

    @Override
    public Object getNativeConsumer() {
        return consumer;
    }

    @Override
    public void subscribe(String topic, String[] tags, MessageListener messageListener) {
        super.subscribe(topic, tags, messageListener);

        try {
            initConsumer();

            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext
                        consumeConcurrentlyContext) {
                    list.forEach(messageExt -> {
                        Message message = new Message(messageExt.getTopic(), new String(messageExt.getBody(),
                                Charsets.UTF_8));
                        message.setExt(messageExt);
                        message.setKeys(messageExt.getKeys());
                        message.setTags(messageExt.getTags());
                        messageListener.onSuccess(message);
                    });
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            String subExpression = "*";
            if (tags != null && tags.length > 0) {
                subExpression = Joiner.on("||").skipNulls().join(tags);
            }
            consumer.subscribe(topic, subExpression);
            consumer.start();
            this.topic = topic;

        } catch (Exception e) {
            e.printStackTrace();
            messageListener.onFailure(e.getCause());
        }
    }

    @Override
    public void unsubscribe() {
        if (consumer != null) {
            if (!Strings.isNullOrEmpty(this.topic)) {
                consumer.unsubscribe(topic);
            }

            consumer.shutdown();
            consumer = null;
            topic = null;
        }
    }

    private void initConsumer() {
        consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr(brokers);
        consumer.setConsumerGroup(groupId);
        consumer.setInstanceName(clientId);
        consumer.setVipChannelEnabled(false);

    }
}
