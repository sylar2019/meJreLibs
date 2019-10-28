package me.java.library.mq.base;

/**
 * @author :  sylar
 * @FileName :  AbstractFactory
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
public abstract class AbstractFactory implements Factory {

    protected void setClient(AbstractClient client, String brokers, String groupId, String clientId) {
        client.setBrokers(brokers);
        client.setGroupId(groupId);
        client.setClientId(clientId);
    }
}
