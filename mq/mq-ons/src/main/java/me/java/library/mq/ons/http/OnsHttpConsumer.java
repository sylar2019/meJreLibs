package me.java.library.mq.ons.http;

import com.google.common.util.concurrent.ListenableScheduledFuture;
import me.java.library.common.service.ConcurrentService;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.ons.AbstractOnsConsumer;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsHttpConsumer extends AbstractOnsConsumer {

    private final static int MAX_MESSAGE_COUNT = 32;
    ListenableScheduledFuture future;

    public OnsHttpConsumer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    @Override
    protected void onSubscribe(String topic, MessageListener messageListener, String... tags) throws Exception {
        ConcurrentService.getInstance().scheduleWithFixedDelay(() -> {
            try {
                subscribeFunc(topic, messageListener);
            } catch (Exception e) {
                e.printStackTrace();
                messageListener.onFailure(e.getCause());
            }
        }, 1, 1, TimeUnit.SECONDS);

    }

    @Override
    protected void onUnsubscribe() throws Exception {
        future.cancel(false);
    }

    private void subscribeFunc(String topic, MessageListener listener) throws Exception {
        List<HttpMsgExt> list = HttpUtil.receiveMsg(
                mqProperties.getBrokers(),
                mqProperties.getAccessKey(),
                mqProperties.getSecretKey(),
                groupId,
                topic,
                MAX_MESSAGE_COUNT
        );

        list.forEach(ext -> {
            Message msg = new Message(topic, ext.getBody());
            msg.setExt(ext);
            msg.setTags(ext.getTag());
            listener.onSuccess(msg);
        });
    }

}
