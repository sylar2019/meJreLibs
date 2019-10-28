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

    /**
     * @throws Exception
     */
    protected abstract void onStart() throws Exception;

    /**
     *
     */
    protected abstract void onStop();

    @Override
    public void start() throws Exception {
        if (!isRunning) {
            stop();
            checkParameters();
            onStart();
            isRunning = true;
        }
    }

    @Override
    public void stop() {
        if (isRunning) {
            onStop();
            isRunning = false;
        }
    }

    protected void checkOnSend(Message message) {
        Preconditions.checkNotNull(message, "message is null");
        Preconditions.checkNotNull(message.getTopic(), "message topic is null");
        Preconditions.checkNotNull(message.getContent(), "message content is null");
    }
}
