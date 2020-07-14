package me.java.library.io.common.codec;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import me.java.library.common.model.pojo.AbstractIdPojo;

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

    protected Channel channel;
    protected Map<String, Map<String, Object>> allAttrs = Maps.newHashMap();

    @Override
    public void initPipeLine(Channel channel) throws Exception {
        this.channel = channel;
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
