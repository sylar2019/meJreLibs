package me.java.library.mq.rocketmq;


import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import me.java.library.mq.base.AbstractConsumer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.base.MqProperties;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

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

    DefaultMQPushConsumer consumer;

    public RocketmqConsumer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    @Override
    protected void onSubscribe(String topic, MessageListener messageListener, String... tags) throws Exception {
        initConsumer();
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            list.forEach(messageExt -> {
                System.out.println("### MessageExt: " + messageExt);

                Message message = new Message(
                        messageExt.getTopic(),
                        new String(messageExt.getBody(), Charsets.UTF_8));
                message.setKey(messageExt.getKeys());
                message.setTag(messageExt.getTags());
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
    }

    @Override
    protected void onUnsubscribe() throws Exception {
        consumer.unsubscribe(topic);
        consumer.shutdown();
        consumer = null;
    }


    private void initConsumer() {
        consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr(mqProperties.getBrokers());
        consumer.setConsumerGroup(groupId);
        consumer.setInstanceName(clientId);
        consumer.setVipChannelEnabled(false);
        //默认是集群消费
        consumer.setMessageModel(MessageModel.CLUSTERING);
    }
}
