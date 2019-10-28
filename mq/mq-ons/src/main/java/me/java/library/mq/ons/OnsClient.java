package me.java.library.mq.ons;

import me.java.library.mq.base.Client;

/**
 * File Name             :  OnsClient
 * Author                :  sylar
 * Create Date           :  2018/4/11
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
public interface OnsClient extends Client {

    /**
     * AccessKey
     *
     * @return AccessKey
     */
    String getAccessKey();

    /**
     * SecretKey
     *
     * @return SecretKey
     */
    String getSecretKey();
}
