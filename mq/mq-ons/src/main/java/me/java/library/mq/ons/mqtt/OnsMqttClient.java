package me.java.library.mq.ons.mqtt;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * File Name             :  OnsMqttClient
 * Author                :  sylar
 * Create Date           :  2018/4/11
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
public class OnsMqttClient {

    private String brokers, clientId;
    private String accessKey, secretKey;
    private MqttClient client;
    private MqttConnectOptions connOpts;
    private EventBus eventBus = new EventBus(getClass().getName());
    private MessageListener messageListener;
    private MqttCallback mqttCallback = new MqttCallback() {
        @Override
        public void connectionLost(Throwable throwable) {
            System.out.println("mqtt connection lost");
            throwable.printStackTrace();
            while (client != null && !client.isConnected()) {
                try {
                    start();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
            Message msg = new Message(topic, new String(mqttMessage.getPayload(), Charsets.UTF_8));
            msg.setExt(mqttMessage);
            onReceived(msg);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            onSendSuccess(token);
        }
    };

    public OnsMqttClient(String brokers, String clientId, String accessKey, String secretKey) {
        this.brokers = brokers;
        this.clientId = clientId;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    private void init() {
        try {
            /**
             * 将accessKey作为MQTT的username
             * 计算签名，将签名作为MQTT的password。
             * 签名的计算方法，参考工具类MacSignature，第一个参数是ClientID的前半部分，即GroupID
             * 第二个参数阿里云的SecretKey
             */
            String mqttUser = accessKey;
            String mqttPwd = MacSignature.macSignature(clientId.split("@@@")[0], secretKey);
            initClient(brokers, clientId, mqttUser, mqttPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initClient(String broker, String clientId, String userName, String password) throws Exception {
        MemoryPersistence persistence = new MemoryPersistence();
        client = new MqttClient(broker, clientId, persistence);
        connOpts = new MqttConnectOptions();
        connOpts.setUserName(userName);
        connOpts.setServerURIs(new String[]{broker});
        connOpts.setPassword(password.toCharArray());
        connOpts.setCleanSession(false);
        connOpts.setKeepAliveInterval(100);
        client.setCallback(mqttCallback);
    }

    public void start() throws MqttException {
        if (client == null) {
            init();
        }

        if (client != null && !client.isConnected()) {
            client.connect(connOpts);
        }
    }

    public void stop() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            client.close();
            client = null;
        }
    }

    public void subscribe(String topic, MessageListener messageListener) throws MqttException {
        this.messageListener = messageListener;

        /*
         * 设置订阅方订阅的Topic集合，此处遵循MQTT的订阅规则，可以是一级Topic，二级Topic，P2P消息请订阅/p2p
         * final String[] topicArray = new String[]{topic + "/notice/", topic + "/p2p"};
         * final int[] qos = {0, 0};
         **/
//        String[] topicArray;

        start();
        client.subscribe(topic);
        System.out.println("订阅：" + topic);
    }

    public void unsubscribe(String topic) throws MqttException {
        if (client != null && client.isConnected()) {
            client.unsubscribe(topic);
        }
    }

    public Object send(Message message) throws Exception {
        start();

        Preconditions.checkState(client.isConnected(), "mqtt client is not connected");
        MqttMessage mqttMessage = new MqttMessage(message.getContent().getBytes());
        mqttMessage.setQos(0);

        System.out.println("topic:" + getMqttTopic(message));
        System.out.println("content:" + message.getContent());
        client.publish(getMqttTopic(message), mqttMessage);
        return null;
    }

    protected void onSendSuccess(IMqttDeliveryToken token) {
        System.out.println("消息发送成功.");
        eventBus.post(new DeliveryCompleteEvent(token));
    }

    protected void onReceived(Message msg) throws Exception {
        System.out.println(String.format("topic:  %s\ncontent:  %s", msg.getTopic(), msg.getContent()));
        if (messageListener != null) {
            messageListener.onSuccess(msg);
        }
    }

    /**
     * P2P
     * 如果发送P2P消息，二级Topic必须是“p2p”，三级Topic是目标的ClientID
     * 此处设置的三级Topic需要是接收方的ClientID
     * <p>
     * notice
     * 消息发送到某个主题Topic，所有订阅这个Topic的设备都能收到这个消息。
     * 遵循MQTT的发布订阅规范，Topic也可以是多级Topic。此处设置了发送到二级Topic
     */
    private String getMqttTopic(Message msg) {
        String targetClientId = msg.getTargetClientId();
        return targetClientId != null ? String.format("%s/p2p/%s", msg.getTopic(), targetClientId)
                : String.format("%s/notice/", msg.getTopic());
    }

    public class DeliveryCompleteEvent {
        IMqttDeliveryToken token;

        public DeliveryCompleteEvent(IMqttDeliveryToken token) {
            this.token = token;
        }
    }

}
