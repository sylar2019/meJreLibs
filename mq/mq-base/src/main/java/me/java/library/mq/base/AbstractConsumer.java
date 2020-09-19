package me.java.library.mq.base;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

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
public abstract class AbstractConsumer extends AbstractClient implements Consumer {

    protected String topic = null;
    protected MessageListener messageListener;

    public AbstractConsumer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    protected abstract void onSubscribe(String topic, MessageListener messageListener, String... tags) throws Exception;

    protected abstract void onUnsubscribe() throws Exception;

    @Override
    final public void subscribe(String topic, MessageListener messageListener, String... tags) {

        try {
            if (this.topic != null) {
                throw new Exception("当前消费者已经订阅：" + this.topic);
            }

            checkOnSubscribe(topic, messageListener);
            this.topic = topic;
            this.messageListener = messageListener;
            onSubscribe(topic, messageListener, tags);
        } catch (Exception e) {
            e.printStackTrace();
            if (messageListener != null) {
                messageListener.onFailure(e);
            }
        }
    }

    @Override
    final public void unsubscribe() {
        if (Strings.isNullOrEmpty(topic)) {
            return;
        }

        try {
            onUnsubscribe();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            topic = null;
            messageListener = null;
        }
    }

    protected void checkOnSubscribe(String topic, MessageListener messageListener) {
        Preconditions.checkState(!Strings.isNullOrEmpty(topic), "topics is null or empty");
        Preconditions.checkNotNull(messageListener, "messageListener is null");
    }

}
