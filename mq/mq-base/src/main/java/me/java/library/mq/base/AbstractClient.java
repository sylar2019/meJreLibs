package me.java.library.mq.base;

import com.google.common.base.Preconditions;

/**
 * @author :  sylar
 * @FileName :  AbstractClient
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
public abstract class AbstractClient implements Client {

    protected MqProperties mqProperties;
    protected String groupId;
    protected String clientId;

    public AbstractClient(MqProperties mqProperties, String groupId, String clientId) {
        this.mqProperties = mqProperties;
        this.groupId = groupId;
        this.clientId = clientId;

        checkParameters();
    }

    @Override
    public MqProperties getMqProperties() {
        return mqProperties;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public void checkParameters() {
        Preconditions.checkNotNull(this.mqProperties, "mqProperties is null");
        Preconditions.checkNotNull(this.mqProperties.getProvider(), "provider is null");
        Preconditions.checkNotNull(this.mqProperties.getBrokers(), "brokers is null");
        Preconditions.checkNotNull(this.groupId, "groupId is null");
        Preconditions.checkNotNull(this.clientId, "clientId is null");
    }
}
