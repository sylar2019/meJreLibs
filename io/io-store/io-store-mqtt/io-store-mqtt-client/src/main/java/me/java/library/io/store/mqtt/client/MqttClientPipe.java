package me.java.library.io.store.mqtt.client;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.cmd.Terminal;
import me.java.library.io.base.pipe.BasePipe;
import me.java.library.io.store.mqtt.common.MqttCmd;
import me.java.library.io.store.mqtt.common.MqttQoS;
import me.java.library.utils.base.ExceptionUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.TimeUnit;

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
public class MqttClientPipe extends BasePipe<MqttClientParams> {

    protected MqttClient client;
    protected MqttClientPersistence persistence;

    public MqttClientPipe(MqttClientParams params) {
        super(params);

        Preconditions.checkNotNull(params.getBroker());
        Preconditions.checkNotNull(params.getClientId());
    }

    @Override
    protected boolean onStart() throws Exception {
        persistence = new MemoryPersistence();
        client = new MqttClient(params.getBroker(),
                params.getClientId(),
                persistence);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                onConnectionChanged(Terminal.LOCAL, false);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                onReceived(new MqttCmd(topic, new String(message.getPayload(), Charsets.UTF_8)));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });

        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setAutomaticReconnect(params.isDaemon());
        connOpts.setUserName(params.getUsername());
        if (!Strings.isNullOrEmpty(params.getPassword())) {
            connOpts.setPassword(params.getPassword().toCharArray());
        }

        client.connect(connOpts);
        return true;
    }

    @Override
    protected boolean onStop() throws Exception {
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
        return true;
    }

    @Override
    protected boolean onSend(Cmd request) throws Exception {
        Preconditions.checkNotNull(client);
        Preconditions.checkState(client.isConnected());
        Preconditions.checkState(request instanceof MqttCmd);

        MqttCmd mqttCmd = (MqttCmd) request;
        MqttMessage message = new MqttMessage(mqttCmd.getContent().getBytes());
        message.setQos(MqttQoS.AT_LEAST_ONCE.value());

        client.publish(mqttCmd.getTopic(), message);

        return true;
    }

    @Override
    protected Cmd onSyncSend(Cmd request, long timeout, TimeUnit unit) throws Exception {
        ExceptionUtils.throwException("mqttClient is not supported to syncSend");
        return null;
    }
}
