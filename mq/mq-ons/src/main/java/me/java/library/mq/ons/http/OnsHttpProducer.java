package me.java.library.mq.ons.http;

import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.ons.AbstractOnsProducer;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsHttpProducer extends AbstractOnsProducer {

    public OnsHttpProducer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    @Override
    protected void onStart() throws Exception {

    }

    @Override
    protected void onStop() throws Exception {

    }

    @Override
    protected Object onSend(Message message) throws Exception {

        HttpResult result = HttpUtil.sendMsg(
                mqProperties.getBrokers(),
                mqProperties.getAccessKey(),
                mqProperties.getSecretKey(),
                groupId,
                message.getTopic(),
                message.getTags(),
                message.getKeys(),
                message.getContent());

        if (result.getStatusCode() == HttpStatusCode.OK_WRITE) {
            return result;
        } else {
            throw new Exception(result.toString());
        }
    }

}
