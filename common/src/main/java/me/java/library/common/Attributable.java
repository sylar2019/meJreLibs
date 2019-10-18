package me.java.library.common;

import java.io.Serializable;
import java.util.Map;

/**
 * File Name             :  Attributable
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
public interface Attributable<K, V> extends Serializable {

    Map<K, V> getAttrs();

    default V getAttr(K k) {
        return getAttrs().get(k);
    }

    default V getAttrOrDefault(K k, V defaultValue) {
        V v = defaultValue;
        if (getAttrs().containsKey(k)) {
            v = getAttr(k);
        }
        return v;
    }

    default void setAttrs(Map<K, V> attrs) {
        if (attrs != null && attrs.size() > 0) {
            getAttrs().putAll(attrs);
        }
    }

    default void setAttr(K k, V v) {
        getAttrs().put(k, v);
    }

    default void setIfAbsent(K k, V v) {
        getAttrs().putIfAbsent(k, v);
    }

    default void remove(K k) {
        getAttrs().remove(k);
    }

    default boolean containsKey(K k) {
        return getAttrs().containsKey(k);
    }

    default boolean containsValue(V v) {
        return getAttrs().containsValue(v);
    }
}
