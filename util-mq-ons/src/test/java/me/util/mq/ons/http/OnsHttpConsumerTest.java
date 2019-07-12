package me.util.mq.ons.http;

import me.util.mq.IConsumer;
import me.util.mq.IFactory;
import me.util.mq.Message;
import me.util.mq.MessageListener;
import me.util.mq.ons.OnsConst;
import me.util.mq.ons.OnsServerConst;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * File Name             :  OnsHttpConsumerTest
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
public class OnsHttpConsumerTest {
    IFactory factory;
    IConsumer consumer;
    OnsConst onsConst;

    @Before
    public void setUp() throws Exception {
        onsConst = OnsConst.getFirst();
        factory = new OnsHttpFactory(onsConst.getAccessKey(), onsConst.getSecretKey());
        consumer = factory.createConsumer(OnsServerConst.TCP_TEST, onsConst.getConsumerId(), "ConsumerClient_1");

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void subscribe() throws Exception {
        consumer.subscribe(onsConst.getTopic(), null, new MessageListener() {
            @Override
            public void onSuccess(Message message) {

                if (message.getExt() instanceof HttpMsgExt) {
                    HttpMsgExt ext = (HttpMsgExt) message.getExt();
                    System.out.println("msgId:" + ext.getMsgId());
                }

                System.out.println(String.format("[TOPIC]:%s [MESSAGE]:%s", message.getTopic(), message.getContent()));
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        Thread.sleep(1000 * 60 * 5);
        System.out.println("test over");
    }

    @Test
    public void unsubscribe() throws Exception {
        consumer.unsubscribe();
    }


}