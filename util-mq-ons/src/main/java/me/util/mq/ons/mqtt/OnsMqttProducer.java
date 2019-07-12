package me.util.mq.ons.mqtt;

import me.util.mq.Message;
import me.util.mq.ons.AbstractOnsProducer;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsMqttProducer extends AbstractOnsProducer {

    private OnsMqttClient client;

    @Override
    protected void onStart() throws Exception {
        client = new OnsMqttClient(brokers, clientId, accessKey, secretKey);
        client.start();
    }

    @Override
    protected void onStop() {
        try {
            client.stop();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getNativeProducer() {
        return client;
    }

    @Override
    public Object send(Message message) throws Exception {
        return client.send(message);
    }

}

