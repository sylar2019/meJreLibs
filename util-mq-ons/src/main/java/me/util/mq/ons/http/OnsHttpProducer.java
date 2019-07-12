package me.util.mq.ons.http;

import me.util.mq.Message;
import me.util.mq.ons.AbstractOnsProducer;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsHttpProducer extends AbstractOnsProducer {


    @Override
    public Object getNativeProducer() {
        return null;
    }

    @Override
    protected void onStart() throws Exception {

    }

    @Override
    protected void onStop() {

    }

    @Override
    public Object send(Message message) throws Exception {

        HttpResult result = HttpUtil.sendMsg(
                brokers,
                getAccessKey(),
                getSecretKey(),
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
