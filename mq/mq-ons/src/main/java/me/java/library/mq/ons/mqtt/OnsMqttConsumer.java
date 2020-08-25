package me.java.library.mq.ons.mqtt;

import me.java.library.mq.base.MessageListener;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.ons.AbstractOnsConsumer;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsMqttConsumer extends AbstractOnsConsumer {
    OnsMqttClient client;

    public OnsMqttConsumer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    @Override
    protected void onSubscribe(String topic, MessageListener messageListener, String... tags) throws Exception {
        client = new OnsMqttClient(mqProperties.getBrokers(),
                clientId,
                mqProperties.getAccessKey(),
                mqProperties.getSecretKey());
        client.subscribe(topic, messageListener);
    }

    @Override
    protected void onUnsubscribe() throws Exception {
        client.unsubscribe(topic);
    }

}
