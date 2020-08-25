package me.java.library.mq.ons.mqtt;

import me.java.library.mq.base.*;
import me.java.library.mq.ons.OnsConst;
import me.java.library.mq.ons.OnsProperties;
import me.java.library.mq.ons.OnsServerConst;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * File Name             :  OnsMqttConsumerTest
 * Author                :  sylar
 * Create Date           :  2018/4/14
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
public class OnsMqttConsumerTest {
    Factory factory;
    Consumer consumer;
    OnsConst onsConst;

    @Before
    public void setUp() throws Exception {
        OnsProperties op = new OnsProperties(MqProperties.PROVIDER_ONS_TCP, OnsServerConst.MQTT_TEST);
        factory = new OnsMqttFactory(op);

        consumer = factory.createConsumer(onsConst.getConsumerId(), MqttConst.ClientId2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void subscribe() throws Exception {

        /*
         * /notice                   mqtt 广播，只要订阅了都能接收
         * /p2p/{targetClientId}     mqtt 点对对,只有targetClientId能接收
         * */
        String[] tags = new String[]{"/notice", "/p2p/" + MqttConst.ClientId2};

        consumer.subscribe(onsConst.getTopic(), new MessageListener() {
            @Override
            public void onSuccess(Message message) {

                if (message.getExt() instanceof MqttMessage) {
                    MqttMessage ext = (MqttMessage) message.getExt();
                    System.out.println("msgId:" + ext.getId());
                }

                System.out.println(String.format("[TOPIC]:%s [MESSAGE]:%s", message.getTopic(), message.getContent()));
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
            }
        }, tags);

        Thread.sleep(1000 * 60 * 5);
        System.out.println("test over");
    }

    @Test
    public void unsubscribe() throws Exception {
        consumer.unsubscribe();
    }


}