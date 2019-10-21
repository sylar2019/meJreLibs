package me.java.library.io.core.edge.sync;

import com.google.common.cache.CacheBuilder;
import me.java.library.common.service.LocalCacheService;
import me.java.library.io.base.Terminal;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  SyncCmdCachesService
 *
 * @Author :  sylar
 * @Create :  2019-10-19
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
public class SyncCmdCachesService extends LocalCacheService<Terminal, SyncCmdCacheService> {
    public SyncCmdCachesService() {
        super(CacheBuilder.newBuilder()
                .maximumSize(100000L)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build());
    }
}
