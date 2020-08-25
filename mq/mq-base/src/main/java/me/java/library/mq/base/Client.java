package me.java.library.mq.base;

/**
 * @author :  sylar
 * @FileName :  Client
 * @CreateDate :  2017/11/7
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
public interface Client {
    /**
     * 获取MQ配置信息
     *
     * @return MQ配置信息
     */
    MqProperties getMqProperties();

    /**
     * 获取当前client端所属groups标识, 即 producerId 或 consumerId
     *
     * @return client端所属group标识
     */
    String getGroupId();


    /**
     * 获取当前client标识
     *
     * @return client标识
     */
    String getClientId();
}
