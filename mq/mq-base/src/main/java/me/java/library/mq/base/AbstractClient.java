package me.java.library.mq.base;

import com.google.common.base.Preconditions;
import me.java.library.utils.base.NetworkUtils;

import java.util.Properties;

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
public abstract class AbstractClient extends Properties implements Client {

    protected String brokers;
    protected String groupId = "DEFAULT";
    protected String clientId = NetworkUtils.getHostMac();

    @Override
    public String getBrokers() {
        return brokers;
    }

    public void setBrokers(String brokers) {
        this.brokers = brokers;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Properties getProperties() {
        return this;
    }

    public void checkParameters() {
        Preconditions.checkNotNull(this.brokers, "brokers is null");
        Preconditions.checkNotNull(this.groupId, "groupId is null");
        Preconditions.checkNotNull(this.clientId, "clientId is null");
    }

}
