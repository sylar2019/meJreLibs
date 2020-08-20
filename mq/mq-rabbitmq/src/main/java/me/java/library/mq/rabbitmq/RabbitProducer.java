package me.java.library.mq.rabbitmq;

import me.java.library.mq.base.AbstractProducer;
import me.java.library.mq.base.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;


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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    protected void onStart() throws Exception {

    }

    @Override
    protected void onStop() {

    }

    @Override
    public Object getNativeProducer() {
        return rabbitTemplate;
    }

    @Override
    public Object send(Message message) throws Exception {
        rabbitTemplate.convertAndSend(message);
        return null;
    }
}
