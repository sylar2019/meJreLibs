package me.java.library.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author :  sylar
 * @FileName :  MessageQueueServiceImpl
 * @CreateDate :  2017/11/08
 * @Description : 基于redis实现的简易队列
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class RedisQueue {

    @Autowired
    RedisOperations redisOperations;

    /**
     * 数据进队列
     *
     * @param queueName 队列名称
     * @param value     入队数据
     */
    public void in(String queueName, String value) {
        redisOperations.opsList(queueName).rightPush(value);
    }

    /**
     * 数据出队列
     *
     * @param queueName 队列名称
     * @return 出队数据
     */
    public String out(String queueName) {
        return redisOperations.opsList(queueName).leftPop();
    }

}
