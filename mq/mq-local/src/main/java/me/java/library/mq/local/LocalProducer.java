package me.java.library.mq.local;

import me.java.library.mq.base.AbstractProducer;
import me.java.library.mq.base.Message;
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
    @Override
    protected void onStart() throws Exception {

    }

    @Override
    protected void onStop() {

    }

    @Override
    public Object getNativeProducer() {
        return null;
    }

    @Override
    public Object send(Message message) throws Exception {
        AsyncEventUtils.postEvent(message);
        return null;
    }
}
