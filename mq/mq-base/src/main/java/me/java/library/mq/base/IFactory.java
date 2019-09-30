package me.java.library.mq.base;

/**
 * @author :  sylar
 * @FileName :  IFactory
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
public interface IFactory {

    /**
     * 创建生产者
     *
     * @param brokers  MQ服务器地址列表
     * @param groupId  client端所属groups标识
     * @param clientId client标识
     * @return MQ消息生产者
     */
    IProducer createProducer(String brokers, String groupId, String clientId);

    /**
     * 创建消费者
     *
     * @param brokers  MQ服务器地址列表
     * @param groupId  client端所属groups标识
     * @param clientId client标识
     * @return MQ消息消费者
     */
    IConsumer createConsumer(String brokers, String groupId, String clientId);
}
