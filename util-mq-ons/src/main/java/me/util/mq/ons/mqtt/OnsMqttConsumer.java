package me.util.mq.ons.mqtt;

import com.google.common.base.Strings;
import me.util.mq.MessageListener;
import me.util.mq.ons.AbstractOnsConsumer;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsMqttConsumer extends AbstractOnsConsumer {
    private OnsMqttClient client;
    private String topic;

    @Override
    public Object getNativeConsumer() {
        return client;
    }

    @Override
    public void subscribe(String topic, String[] tags, MessageListener messageListener) {
        super.subscribe(topic, tags, messageListener);

        if (client == null) {
            client = new OnsMqttClient(brokers, clientId, accessKey, secretKey);
        }

        try {
            client.subscribe(topic, messageListener);
            this.topic = topic;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unsubscribe() {
        try {
            if (Strings.isNullOrEmpty(topic)) {
                client.unsubscribe(topic);
                topic = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
