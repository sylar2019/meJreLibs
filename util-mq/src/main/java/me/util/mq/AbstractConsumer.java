package me.util.mq;

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
public abstract class AbstractConsumer extends AbstractClient implements IConsumer {

    @Override
    public void subscribe(String topic, String[] tags, MessageListener messageListener) {
        checkOnSubscribe(topic, messageListener);
    }

    protected void checkOnSubscribe(String topic, MessageListener messageListener) {
        Preconditions.checkNotNull(topic, "topics is null");
        Preconditions.checkArgument(!topic.isEmpty(), "topics is empty");
        Preconditions.checkNotNull(messageListener, "messageListener is null");
    }

}
