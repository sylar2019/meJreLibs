package me.java.library.mq.rabbitmq;


import com.rabbitmq.client.*;
import me.java.library.mq.base.AbstractConsumer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.base.MqProperties;
import me.java.library.utils.spring.SpringBeanUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

    ConnectionFactory factory;
    Connection connection;
    Channel channel;
    String consumerTag;

    public RabbitConsumer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
        factory = SpringBeanUtils.getBean(ConnectionFactory.class);
    }

    @Override
    protected void onSubscribe(String topic, MessageListener messageListener, String... tags) throws Exception {
        String exchangeName = RabbitConfig.EXCHANGE_NAME;
        String queueName = topic;

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
        consumerTag = channel.basicConsume(queueName, false, consumer);
    }

    @Override
    protected void onUnsubscribe() throws Exception {
        channel.basicCancel(consumerTag);
        channel.close();
        connection.close();
    }

}
