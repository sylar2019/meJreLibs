package me.java.library.mq.rabbitmq;


import me.java.library.mq.base.AbstractConsumer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

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
    @Override
    public Object getNativeConsumer() {
        return null;
    }

    @Override
    public void subscribe(String topic, MessageListener messageListener, String... tags) {
        super.subscribe(topic, messageListener, tags);
    }

    @Override
    public void unsubscribe() {

    }

    @RabbitListener(queues = RabbitConfig.IOT_QUEUE)
    public void onReceived(Message message) {

    }

}
