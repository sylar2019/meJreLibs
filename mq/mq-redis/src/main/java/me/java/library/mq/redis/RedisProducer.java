package me.java.library.mq.redis;

import me.java.library.mq.base.AbstractProducer;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MqProperties;
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

    RedisNotice redisNotice;

    public RedisProducer(MqProperties mqProperties, String groupId, String clientId) {
        super(mqProperties, groupId, clientId);
        redisNotice = SpringBeanUtils.getBean(RedisNotice.class);
    }


    @Override
    protected void onStart() throws Exception {

    }

    @Override
    protected void onStop() throws Exception {

    }

    @Override
    protected Object onSend(Message message) throws Exception {
        redisNotice.publish(message.getTopic(), message.getContent());
        return null;
    }
}
