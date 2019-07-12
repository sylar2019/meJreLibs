package me.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author :  sylar
 * @FileName :  RedisStack
 * @CreateDate :  2017/11/08
 * @Description : 基于redis实现的简易堆栈
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
public class RedisStack {

    @Autowired
    RedisOperations redisOperations;


    /**
     * 数据进栈
     *
     * @param stackName 堆栈名称
     * @param value     入栈数据
     */
    public void push(String stackName, String value) {
        redisOperations.opsList(stackName).rightPush(value);

    }

    /**
     * 数据出栈
     *
     * @param stackName 堆栈名称
     * @return 出栈数据
     */
    public String pop(String stackName) {
        return redisOperations.opsList(stackName).rightPop();
    }
}
