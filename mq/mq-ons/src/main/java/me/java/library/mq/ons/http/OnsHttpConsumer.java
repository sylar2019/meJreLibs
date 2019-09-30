package me.java.library.mq.ons.http;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.ons.AbstractOnsConsumer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by sylar on 2017/1/6.
 */
public class OnsHttpConsumer extends AbstractOnsConsumer {

    private final static int MAX_MESSAGE_COUNT = 32;
    private final static int POLL_INTERVAL = 100;
    private ExecutorService executorService;

    @Override
    public Object getNativeConsumer() {
        return null;
    }

    @Override
    public void subscribe(String topic, String[] tags, MessageListener messageListener) {
        super.subscribe(topic, tags, messageListener);

        executorService = new ThreadPoolExecutor(
                10,
                Integer.MAX_VALUE,
                10L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("OnsHttpConsumer-pull-pool-%d").build());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        onSubscribe(topic, messageListener);
                        Thread.sleep(POLL_INTERVAL);
                    } catch (Exception e) {
                        messageListener.onFailure(e.getCause());
                    }
                }
            }
        });
    }

    @Override
    public void unsubscribe() {
        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
    }

    private void onSubscribe(String topic, MessageListener listener) throws Exception {
        List<HttpMsgExt> list = HttpUtil.receiveMsg(
                brokers,
                getAccessKey(),
                getSecretKey(),
                groupId,
                topic,
                MAX_MESSAGE_COUNT
        );

        if (list != null) {
            list.forEach(ext -> {
                Message msg = new Message(topic, ext.getBody());
                msg.setExt(ext);
                msg.setTags(ext.getTag());
                listener.onSuccess(msg);
            });
        }
    }

}
