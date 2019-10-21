package me.java.library.io.core.bean;

import io.netty.channel.Channel;
import me.java.library.io.base.Terminal;
import me.java.library.common.service.LocalCacheService;

/**
 * File Name             :  ChannelCacheService
 *
 * @Author :  sylar
 * @Create :  2019-10-16
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class ChannelCacheService extends LocalCacheService<Terminal, Channel> {

    private static ChannelCacheService instance = new ChannelCacheService();

    private ChannelCacheService() {
    }

    synchronized public static ChannelCacheService getInstance() {
        return instance;
    }
}
