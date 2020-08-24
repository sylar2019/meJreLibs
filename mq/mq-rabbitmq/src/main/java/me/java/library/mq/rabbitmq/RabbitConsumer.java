package me.java.library.mq.rabbitmq;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rabbitmq.client.*;
import me.java.library.mq.base.AbstractConsumer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import me.java.library.utils.spring.SpringBeanUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * @author :  sylar
 * @FileName :  RocketmqConsumer
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
public class RabbitConsumer extends AbstractConsumer {

    Connection connection;
    Channel channel;
    Map<String, Set<String>> subscribers = Maps.newHashMap();
    private final ConnectionFactory factory;

    public RabbitConsumer() {
        factory = SpringBeanUtils.getBean(ConnectionFactory.class);
    }

    @Override
    public Object getNativeConsumer() {
        return connection;
    }

    @Override
    public void subscribe(String topic, MessageListener messageListener, String... tags) {
        super.subscribe(topic, messageListener, tags);
        String exchangeName = RabbitConfig.EXCHANGE_NAME;
        String queueName = topic;

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            //创建队列
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, exchangeName, queueName);
            channel.basicQos(1);

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String content = new String(body, StandardCharsets.UTF_8);
                    Message message = new Message(topic, content);
                    messageListener.onSuccess(message);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
            String consumerTag = channel.basicConsume(queueName, false, consumer);
            addSubscriber(topic, consumerTag);
        } catch (Exception e) {
            messageListener.onFailure(e);
        }

    }

    @Override
    public void unsubscribe() {
        if (channel == null) {
            return;
        }

        subscribers.values().forEach(set -> {
            set.forEach(tag -> {
                try {
                    channel.basicCancel(tag);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        try {
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void addSubscriber(String topic, String consumerTag) {
        if (!subscribers.containsKey(topic)) {
            subscribers.put(topic, Sets.newHashSet());
        }

        subscribers.get(topic).add(consumerTag);
    }

}
