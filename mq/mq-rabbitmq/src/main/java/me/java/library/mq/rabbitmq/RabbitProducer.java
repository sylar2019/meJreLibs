package me.java.library.mq.rabbitmq;

//import com.rabbitmq.client.*;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import me.java.library.mq.base.AbstractProducer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
import me.java.library.utils.spring.SpringBeanUtils;


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

    ConnectionFactory factory;
    Connection connection;

    public RabbitProducer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
        factory = SpringBeanUtils.getBean(ConnectionFactory.class);
    }

    @Override
    protected void onStart() throws Exception {
        connection = factory.newConnection();
    }

    @Override
    protected void onStop() throws Exception {
        connection.close();
    }

    @Override
    protected Object onSend(Message message) throws Exception {
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
