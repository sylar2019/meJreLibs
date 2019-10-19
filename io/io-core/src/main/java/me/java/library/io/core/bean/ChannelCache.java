package me.java.library.io.core.bean;

import io.netty.channel.Channel;
import me.java.library.io.base.Terminal;
import me.java.library.utils.base.LocalCache;

/**
 * File Name             :  ChannelCache
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
public class ChannelCache extends LocalCache<Terminal, Channel> {

    private static ChannelCache instance = new ChannelCache();

    private ChannelCache() {
    }

    synchronized public static ChannelCache getInstance() {
        return instance;
    }
}
