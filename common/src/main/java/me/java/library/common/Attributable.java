package me.java.library.common;

import java.io.Serializable;
import java.util.Map;

/**
 * File Name             :  Attributable
 *
 * @author :  sylar
 * Create :  2019-10-16
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
public interface Attributable extends Serializable {

    Map<String, Object> getAttrs();

    default void setAttrs(Map<String, Object> attrs) {
        if (attrs != null && attrs.size() > 0) {
            getAttrs().putAll(attrs);
        }
    }

    default <V> V getOrDefault(String key, V defaultValue) {
        V v = defaultValue;
        if (containsKey(key)) {
            v = getAttr(key);
        }
        return v;
    }

    @SuppressWarnings("unchecked")
    default <V> V getAttr(String key) {
        return (V) getAttrs().get(key);
    }


    default void setAttr(String key, Object value) {
        getAttrs().put(key, value);
    }

    default void remove(String key) {
        getAttrs().remove(key);
    }

    default boolean containsKey(String key) {
        return getAttrs().containsKey(key);
    }

    default boolean containsValue(Object value) {
        return getAttrs().containsValue(value);
    }
}
