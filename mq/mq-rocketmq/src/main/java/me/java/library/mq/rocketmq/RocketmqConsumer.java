package me.java.library.mq.rocketmq;


import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import me.java.library.mq.base.AbstractConsumer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;

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
    public void subscribe(String topic, MessageListener messageListener, String... tags) {
        super.subscribe(topic, messageListener, tags);

        try {
            initConsumer();

            consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
                list.forEach(messageExt -> {
                    Message message = new Message(messageExt.getTopic(),
                            new String(messageExt.getBody(),
                                    Charsets.UTF_8));
                    message.setExt(messageExt);
                    message.setKeys(messageExt.getKeys());
                    message.setTags(messageExt.getTags());
                    messageListener.onSuccess(message);
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
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
