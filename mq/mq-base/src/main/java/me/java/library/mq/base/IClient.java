package me.java.library.mq.base;

import java.util.Properties;

/**
 * @author :  sylar
 * @FileName :  IClient
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
public interface IClient {

    /**
     * 获取MQ服务端地址
     *
     * @return 服务端地址列表，多个地址一般是逗号分隔
     */
    String getBrokers();

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

    /**
     * 获取Client属性配置
     *
     * @return Client属性配置
     */
    Properties getProperties();

}
