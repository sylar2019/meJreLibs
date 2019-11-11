package me.java.library.common.model.pojo;

import com.google.common.collect.Maps;
import me.java.library.common.Attributable;
import me.java.library.utils.base.JsonUtils;

import java.util.Map;

/**
 * File Name             :  AbstractPojo
 *
 * @author :  sylar
 * <p>
 * Create Date           :  2018/4/19
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
public abstract class AbstractPojo implements Attributable<String, Object> {

    protected Map<String, Object> attrs = Maps.newHashMap();

    @Override
    public Map<String, Object> getAttrs() {
        return attrs;
    }

    @SuppressWarnings("unchecked")
    public <V> V getOrDefault(String attrKey, V defaultValue) {
        return (V) getAttrOrDefault(attrKey, defaultValue);
    }

    @Override
    public String toString() {
        return JsonUtils.toJSONString(this);
    }
}
