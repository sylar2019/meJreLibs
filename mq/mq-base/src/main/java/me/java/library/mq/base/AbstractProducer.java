package me.java.library.mq.base;

import com.google.common.base.Preconditions;

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
public abstract class AbstractProducer extends AbstractClient implements Producer {

    protected boolean isRunning;

    public AbstractProducer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
    }

    protected abstract void onStart() throws Exception;

    protected abstract void onStop() throws Exception;

    protected abstract Object onSend(Message message) throws Exception;

    @Override
    final public void start() {
        if (isRunning) {
            return;
        }

        try {
            checkParameters();
            onStart();
            isRunning = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    final public void stop() {
        if (!isRunning) {
            return;
        }

        try {
            onStop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isRunning = false;
        }
    }

    @Override
    final public Object send(Message message) {

        try {
            Preconditions.checkState(isRunning, "producer is not ready");
            checkOnSend(message);
            return onSend(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void checkOnSend(Message message) {
        Preconditions.checkNotNull(message, "message is null");
        Preconditions.checkNotNull(message.getTopic(), "message topic is null");
        Preconditions.checkNotNull(message.getContent(), "message content is null");
    }
}
