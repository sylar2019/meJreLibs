package me.java.library.io.core.cloud;

import me.java.library.common.Disposable;
import me.java.library.mq.base.Factory;
import me.java.library.mq.rocketmq.RocketmqFactory;
import me.java.library.utils.base.NetworkUtils;

/**
 * File Name             :  AbstractMqClient
 *
 * @author :  sylar
 * Create                :  2019-10-23
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AbstractMqClient implements Disposable {
    protected Factory factory = new RocketmqFactory();

    protected abstract String getGroupId();

    @Override
    public void dispose() {

    }

    protected String getClientId() {
        return NetworkUtils.getHostMac();
    }
}
