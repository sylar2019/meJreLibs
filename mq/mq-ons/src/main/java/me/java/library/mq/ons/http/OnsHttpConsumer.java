package me.java.library.mq.ons.http;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQConsumer;
import com.aliyun.mq.http.model.AsyncCallback;
import com.google.common.base.Strings;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.ons.AbstractOnsConsumer;
import me.java.library.mq.ons.Utils;

import java.util.Collections;
import java.util.List;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsHttpConsumer extends AbstractOnsConsumer {

    /**
     * 一次最多消费 10 条(最多可设置为16条)
     */
    private final static int NUM_CONSUMED_ONCE = 10;

    /**
     * 轮询间隔 3 秒（最多可设置为30秒）
     */
    private final static int POLLING_SECOND = 1;

    /**
     * Topic 所属实例 ID，默认实例为空
     */
    String instanceId;
    MQClient mqClient;

    public OnsHttpConsumer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
        instanceId = mqProperties.getAttr("instanceId");

        mqClient = new MQClient(
                mqProperties.getBrokers(),
                mqProperties.getAccessKey(),
                mqProperties.getSecretKey());
    }

    @Override
    protected void onSubscribe(String topic, MessageListener messageListener, String... tags) throws Exception {
        final MQConsumer consumer;
        if (Strings.isNullOrEmpty(instanceId)) {
            consumer = mqClient.getConsumer(topic, groupId);
        } else {
            String msgTag = Utils.tagsFromArray(tags);
            consumer = mqClient.getConsumer(instanceId, topic, groupId, msgTag);
        }

        consumer.asyncConsumeMessage(
                NUM_CONSUMED_ONCE,
                POLLING_SECOND,
                new AsyncCallback<List<com.aliyun.mq.http.model.Message>>() {
                    @Override
                    public void onSuccess(List<com.aliyun.mq.http.model.Message> result) {
                        result.forEach(msg -> processHttpMsg(consumer, msg));
                    }

                    @Override
                    public void onFail(Exception ex) {
                        messageListener.onFailure(ex);
                    }
                });

    }

    @Override
    protected void onUnsubscribe() throws Exception {
        mqClient.close();
    }

    void processHttpMsg(MQConsumer consumer, com.aliyun.mq.http.model.Message message) {

        List<String> handles = Collections.singletonList(message.getReceiptHandle());
        try {
            Message msg = new Message(topic, message.getMessageBodyString());
            msg.setExt(message);
            msg.setKeys(message.getMessageKey());
            msg.setTags(message.getMessageTag());
            messageListener.onSuccess(msg);

            //同步确认
//            consumer.ackMessage(handles);

            //异步确认
            consumer.asyncAckMessage(handles, new AsyncCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                }

                @Override
                public void onFail(Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
