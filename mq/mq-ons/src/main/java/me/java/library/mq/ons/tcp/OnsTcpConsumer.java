package me.java.library.mq.ons.tcp;


import com.aliyun.openservices.ons.api.*;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.ons.AbstractOnsConsumer;

import java.util.Properties;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsTcpConsumer extends AbstractOnsConsumer {

    Consumer consumer;

    @Override
    public Object getNativeConsumer() {
        return consumer;
    }

    @Override
    public void subscribe(String topic, String[] tags, MessageListener messageListener) {
        super.subscribe(topic, tags, messageListener);

        try {
            initConsumer();

            String subExpression = "*";
            if (tags != null && tags.length > 0) {
                subExpression = Joiner.on("||").skipNulls().join(tags);
            }

            consumer.subscribe(topic, subExpression, new com.aliyun.openservices.ons.api.MessageListener() {
                @Override
                public Action consume(com.aliyun.openservices.ons.api.Message message, ConsumeContext context) {
                    String content = new String(message.getBody(), Charsets.UTF_8);
                    System.out.println("Receive Msg: " + message);

                    try {
                        Message msg = new Message(topic, content);
                        msg.setExt(message);
                        msg.setKeys(message.getKey());
                        msg.setTags(message.getTag());
                        messageListener.onSuccess(msg);
                        return Action.CommitMessage;
                    } catch (Exception e) {
                        String err = String.format("处理消息发生异常. msgId:%s\ncontent:%s\n%s", message.getMsgID(), content, e
                                .getMessage());
                        System.out.println(err);
                        e.printStackTrace();

                        return Action.ReconsumeLater;
                    }
                }
            });

            consumer.start();

        } catch (Exception e) {
            e.printStackTrace();
            messageListener.onFailure(e.getCause());
        }
    }


    @Override
    public void unsubscribe() {
        if (consumer != null) {
            consumer.shutdown();
            consumer = null;
        }
    }

    private void initConsumer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, getSecretKey());
        properties.put(PropertyKeyConst.ONSAddr, brokers);
        properties.put(PropertyKeyConst.ConsumerId, groupId);
        consumer = ONSFactory.createConsumer(properties);
    }

}
