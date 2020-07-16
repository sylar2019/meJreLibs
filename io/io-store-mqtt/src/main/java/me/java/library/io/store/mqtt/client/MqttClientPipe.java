package me.java.library.io.store.mqtt.client;

import com.google.common.base.Preconditions;
import io.netty.handler.codec.mqtt.MqttQoS;
import me.java.library.io.common.cmd.Cmd;
import me.java.library.io.common.pipe.AbstractPipe;
import me.java.library.io.store.mqtt.MqttCmdNode;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * File Name             :  MqttClientPipe
 *
 * @author :  sylar
 * Create                :  2020/7/14
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public class MqttClientPipe extends AbstractPipe {

    MqttClient client;
    MqttClientPersistence persistence;

    @Override
    protected void onStart() throws Exception {
        String broker = "tcp://iot.eclipse.org:1883";
        String clientId = "JavaSample";
        persistence = new MemoryPersistence();
        client = new MqttClient(broker, clientId, persistence);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        client.connect(connOpts);
    }

    @Override
    protected void onStop() throws Exception {
        if (client != null) {
            client.disconnect();
            client.close();
            client = null;
        }
        if (persistence != null) {
            persistence.clear();
            persistence.close();
            persistence = null;
        }
    }

    @Override
    protected void onSend(Cmd cmd) throws Exception {
        Preconditions.checkNotNull(client);
        Preconditions.checkState(client.isConnected());
        Preconditions.checkState(cmd instanceof MqttCmdNode);

        MqttCmdNode mqttCmd = (MqttCmdNode) cmd;
        MqttMessage message = new MqttMessage(mqttCmd.getContent().getBytes());
        message.setQos(MqttQoS.EXACTLY_ONCE.value());

        client.publish(mqttCmd.getTopic(), message);
    }
}
