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
public abstract class AbstractConsumer extends AbstractClient implements Consumer {

    @Override
    public void subscribe(String topic, MessageListener messageListener, String... tags) {
        checkOnSubscribe(topic, messageListener);
    }

    protected void checkOnSubscribe(String topic, MessageListener messageListener) {
        Preconditions.checkNotNull(topic, "topics is null");
        Preconditions.checkArgument(!topic.isEmpty(), "topics is empty");
        Preconditions.checkNotNull(messageListener, "messageListener is null");
    }

}
