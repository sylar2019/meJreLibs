package me.java.library.io.common.codec;

import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandler;
import me.java.library.common.model.pojo.AbstractIdPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected LinkedHashMap<String, ChannelHandler> handlers = Maps.newLinkedHashMap();
    protected Map<String, Map<String, Object>> allAttrs = Maps.newHashMap();

    @Override
    public LinkedHashMap<String, ChannelHandler> getChannelHandlers() {
        putHandlers(handlers);
        return handlers;
    }

    protected void putHandlers(LinkedHashMap<String, ChannelHandler> handlers) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V getHandlerAttr(String handlerKey, String attrKey, V defaultValue) {
        V v = defaultValue;
        Map<String, Object> handlerAttrs = getHandlerAttrs(handlerKey);
        if (handlerAttrs.containsKey(attrKey)) {
            v = (V) handlerAttrs.get(attrKey);
        }
        return v;
    }

    public <V> void setHandlerAttr(String handlerKey, String attrKey, V attrValue) {
        Map<String, Object> handlerAttrs = getHandlerAttrs(handlerKey);
        handlerAttrs.put(attrKey, attrValue);
    }

    protected Map<String, Object> getHandlerAttrs(String handlerKey) {
        if (!allAttrs.containsKey(handlerKey)) {
            allAttrs.put(handlerKey, Maps.newHashMap());
        }
        return allAttrs.get(handlerKey);
    }


}
