package me.java.library.io.core.utils;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import me.java.library.io.base.pipe.Pipe;

/**
 * File Name             :  ChannelAttr
 *
 * @author :  sylar
 * Create :  2019-10-17
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
public interface ChannelAttr {
    AttributeKey<Pipe> ATTR_PIPE = AttributeKey.valueOf("pipe");

    static <V> V get(Channel channel, AttributeKey<V> attrKey) {
        return channel.attr(attrKey).get();
    }

    static <V> void set(Channel channel, AttributeKey<V> attrKey, V v) {
        channel.attr(attrKey).set(v);
    }
}
