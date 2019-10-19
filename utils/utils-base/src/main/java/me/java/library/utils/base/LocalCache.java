package me.java.library.utils.base;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Map;

/**
 * File Name             :  LocalCache
 * Author                :  sylar
 * Create Date           :  2018/4/18
 * Description           :  基于guava实现的本地缓存
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
public class LocalCache<K, V> {

    private Cache<K, V> cache;

    public LocalCache() {
        this(CacheBuilder
                .newBuilder()
                .maximumSize(1000000L)
                .build());
    }

    public LocalCache(Cache<K, V> cache) {
        this.cache = cache;
    }

    public V get(K k) {
        return cache.getIfPresent(k);
    }

    public void put(K key, V value) {
        if (value != cache.getIfPresent(key)) {
            cache.put(key, value);
        }
    }

    public void remoceAll() {
        cache.invalidateAll();
    }

    public void remove(K key) {
        cache.invalidate(key);
    }

    public boolean containsKey(K key) {
        return cache.getIfPresent(key) != null;
    }

    public long size() {
        return cache.size();
    }

    public Map<K, V> getMap() {
        return cache.asMap();
    }
}
