package me.java.library.io.core.codec;

import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandler;
import me.java.library.common.model.pojo.AbstractIdPojo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * File Name             :  AbstractCodec
 *
 * @author :  sylar
 * Create :  2019-10-14
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
public abstract class AbstractCodec extends AbstractIdPojo<String> implements Codec {

    protected LinkedHashMap<String, ChannelHandler> handlers = Maps.newLinkedHashMap();
    protected Map<String, Map<String, Object>> allAttrs = Maps.newHashMap();

    @Override
    public LinkedHashMap<String, ChannelHandler> getChannelHandlers() {
        return handlers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V getHandlerAttr(String handlerKey, String attrKey, V defaultValue) {
        V v = defaultValue;
        if (allAttrs.containsKey(handlerKey)) {
            Map<String, Object> attrs = allAttrs.get(handlerKey);
            if (attrs.containsKey(attrKey)) {
                v = (V) attrs.get(attrKey);
            }
        }
        return v;
    }

}
