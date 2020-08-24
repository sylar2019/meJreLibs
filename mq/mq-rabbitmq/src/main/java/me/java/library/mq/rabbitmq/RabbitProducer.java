package me.java.library.mq.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import me.java.library.mq.base.AbstractProducer;
import me.java.library.mq.base.Message;
import me.java.library.utils.spring.SpringBeanUtils;

import java.io.IOException;


/**
 * @author :  sylar
 * @FileName :
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
public class RabbitProducer extends AbstractProducer {
    Connection connection;
    private final ConnectionFactory factory;

    public RabbitProducer() {
        factory = SpringBeanUtils.getBean(ConnectionFactory.class);
    }

    @Override
    protected void onStart() throws Exception {
        connection = factory.newConnection();
    }

    @Override
    protected void onStop() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getNativeProducer() {
        return connection;
    }

    @Override
    public Object send(Message message) throws Exception {
        assert message != null;
        assert message.getTopic() != null;
        assert message.getContent() != null;

        String exchangeName = RabbitConfig.EXCHANGE_NAME;
        String queueName = message.getTopic();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicPublish(exchangeName, queueName, null, message.getContent().getBytes());
        channel.close();

        return null;
    }
}
