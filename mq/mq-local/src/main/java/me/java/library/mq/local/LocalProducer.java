package me.java.library.mq.local;

import me.java.library.mq.base.AbstractProducer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
import me.java.library.utils.base.guava.AsyncEventUtils;


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
public class LocalProducer extends AbstractProducer {

    public LocalProducer(MqProperties mqProperties, String groupId, String clientId) {
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
        AsyncEventUtils.postEvent(message);
        return null;
    }

}
