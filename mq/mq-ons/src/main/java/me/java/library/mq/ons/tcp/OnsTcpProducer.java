package me.java.library.mq.ons.tcp;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.google.common.base.Charsets;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.ons.AbstractOnsProducer;

import java.util.Properties;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsTcpProducer extends AbstractOnsProducer {

    Producer producer;

    public OnsTcpProducer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    @Override
    protected void onStart() throws Exception {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, mqProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, mqProperties.getSecretKey());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, mqProperties.getBrokers());
        properties.put(PropertyKeyConst.GROUP_ID, groupId);
        producer = ONSFactory.createProducer(properties);
        producer.start();
    }

    @Override
    protected void onStop() {
        producer.shutdown();
        producer = null;
    }

    @Override
    protected Object onSend(Message message) throws Exception {
        return producer.send(new com.aliyun.openservices.ons.api.Message(
                message.getTopic(),
                message.getTag(),
                message.getKey(),
                message.getContent().getBytes(Charsets.UTF_8))
        );
    }
}