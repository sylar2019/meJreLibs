package me.java.library.mq.ons.mqtt;

import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.ons.AbstractOnsProducer;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsMqttProducer extends AbstractOnsProducer {

    OnsMqttClient client;

    public OnsMqttProducer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    @Override
    protected void onStart() throws Exception {
        client = new OnsMqttClient(mqProperties.getBrokers(),
                clientId,
                mqProperties.getAccessKey(),
                mqProperties.getSecretKey());
        client.start();
    }

    @Override
    protected void onStop() throws Exception {
        client.stop();
    }

    @Override
    protected Object onSend(Message message) throws Exception {
        return client.send(message);
    }

}

