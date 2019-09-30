package me.java.library.mq.ons.tcp;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.google.common.base.Charsets;
import me.java.library.mq.base.Message;
import me.java.library.mq.ons.AbstractOnsProducer;

import java.util.Properties;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsTcpProducer extends AbstractOnsProducer {

    protected Producer producer;

    @Override
    public Object getNativeProducer() {
        return producer;
    }

    @Override
    protected void onStart() throws Exception {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, getSecretKey());
        properties.put(PropertyKeyConst.ONSAddr, brokers);
        properties.put(PropertyKeyConst.ProducerId, groupId);
        producer = ONSFactory.createProducer(properties);
        producer.start();
    }

    @Override
    protected void onStop() {
        if (producer != null) {
            producer.shutdown();
            producer = null;
        }
    }

    @Override
    public Object send(Message message) throws Exception {
        return producer.send(new com.aliyun.openservices.ons.api.Message(
                message.getTopic(),
                message.getTags(),
                message.getKeys(),
                message.getContent().getBytes(Charsets.UTF_8))
        );
    }


}