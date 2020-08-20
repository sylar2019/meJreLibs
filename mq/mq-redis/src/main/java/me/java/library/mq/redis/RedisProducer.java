package me.java.library.mq.redis;

import me.java.library.mq.base.AbstractProducer;
import me.java.library.mq.base.Message;
import me.java.library.utils.redis.RedisNotice;
import me.java.library.utils.spring.SpringBeanUtils;


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
public class RedisProducer extends AbstractProducer {

    private final RedisNotice redisNotice;

    public RedisProducer() {
        redisNotice = SpringBeanUtils.getBean(RedisNotice.class);
    }

    @Override
    public Object getNativeProducer() {
        return redisNotice;
    }

    @Override
    protected void onStart() throws Exception {

    }

    @Override
    protected void onStop() {

    }

    @Override
    public Object send(Message message) throws Exception {
        checkOnSend(message);
        redisNotice.publish(message.getTopic(), message.getContent());
        return null;
    }

}
