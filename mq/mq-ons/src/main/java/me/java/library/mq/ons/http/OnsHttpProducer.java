package me.java.library.mq.ons.http;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQProducer;
import com.aliyun.mq.http.model.TopicMessage;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.ons.AbstractOnsProducer;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsHttpProducer extends AbstractOnsProducer {

    /**
     * Topic 所属实例 ID，默认实例为空
     */
    String instanceId;
    MQClient mqClient;

    public OnsHttpProducer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
        instanceId = mqProperties.getAttr("instanceId");

        mqClient = new MQClient(
                mqProperties.getBrokers(),
                mqProperties.getAccessKey(),
                mqProperties.getSecretKey());
    }

    @Override
    protected void onStart() throws Exception {

    }

    @Override
    protected void onStop() throws Exception {
        mqClient.close();
    }

    @Override
    protected Object onSend(Message message) throws Exception {
        final String topic = message.getTopic();
        MQProducer producer;
        if (Strings.isNullOrEmpty(instanceId)) {
            producer = mqClient.getProducer(topic);
        } else {
            producer = mqClient.getProducer(instanceId, topic);
        }

        byte[] body = message.getContent().getBytes(Charsets.UTF_8);
        TopicMessage pubMsg = new TopicMessage(body, message.getTags());
        if (message.getKeys() != null) {
            pubMsg.setMessageKey(message.getKeys());
        }

        return producer.publishMessage(pubMsg);
    }

}
