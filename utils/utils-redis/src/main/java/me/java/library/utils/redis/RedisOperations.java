package me.java.library.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author :  sylar
 * @FileName :  RedisOperations
 * @CreateDate :  2017/11/08
 * @Description : redis 的6大类数据类型操作简易封装
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class RedisOperations {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public boolean expire(String key, final long timeout, final TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    public boolean expireAt(String key, final Date date) {
        return stringRedisTemplate.expireAt(key, date);
    }

    public long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    public long getExpire(String key, final TimeUnit timeUnit) {
        return stringRedisTemplate.getExpire(key, timeUnit);
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    public void rename(String oldKey, String newKey) {
        stringRedisTemplate.rename(oldKey, newKey);
    }

    public BoundValueOperations<String, String> opsValue(String key) {
        return stringRedisTemplate.boundValueOps(key);
    }

    public BoundHashOperations<String, String, String> opsMap(String key) {
        return stringRedisTemplate.boundHashOps(key);
    }

    public BoundListOperations<String, String> opsList(String key) {
        return stringRedisTemplate.boundListOps(key);
    }

    public BoundSetOperations<String, String> opsSet(String key) {
        return stringRedisTemplate.boundSetOps(key);
    }

    public BoundZSetOperations<String, String> opsZSet(String key) {
        return stringRedisTemplate.boundZSetOps(key);
    }

    public BoundGeoOperations<String, String> opsGeo(String key) {
        return stringRedisTemplate.boundGeoOps(key);
    }

}
