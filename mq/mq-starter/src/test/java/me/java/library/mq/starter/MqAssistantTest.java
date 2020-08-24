package me.java.library.mq.starter;

import com.google.common.util.concurrent.ListenableScheduledFuture;
import me.java.library.common.service.ConcurrentService;
import me.java.library.mq.base.Consumer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.base.Producer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author : sylar
 * @fullName : me.java.library.mq.starter.MqAssistantTest
 * @createDate : 2020/8/19
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MqAssistantTest {

    final static String TOPIC = "TOPIC_001";

    @Autowired
    MqAssistant assistant;
    Producer producer;
    Consumer consumer;

    @Before
    public void init() throws Exception {
        producer = assistant.createProducer("PID_001", "CLIENT_001");
        consumer = assistant.createConsumer("CID_1001", "CLIENT_001");
        producer.start();
    }

    @After
    public void dispose() {
        producer.stop();
        consumer.unsubscribe();
        ConcurrentService.getInstance().dispose();
        System.out.println("### dispose");
    }

    @Test
    public void startTest() throws Exception {
        System.out.println("### being");

        startSubscribe();

        CountDownLatch cd = new CountDownLatch(10);
        ListenableScheduledFuture<?> future = ConcurrentService.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sendMsg();
                cd.countDown();
            }
        }, 1, 1, TimeUnit.SECONDS);

        cd.await();
        future.cancel(false);

        System.out.println("### end");
    }


    void startSubscribe() {
        ConcurrentService.getInstance().postRunnable(() -> consumer.subscribe(TOPIC, new MessageListener() {
            @Override
            public void onSuccess(Message message) {
                System.out.println("### 接收 ###\t" + message + "\n");
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }));
    }


    void sendMsg() {
        Message msg = new Message(TOPIC, "this is a test message");
        try {
            producer.send(msg);
            System.out.println("### 发送 ###\t" + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}