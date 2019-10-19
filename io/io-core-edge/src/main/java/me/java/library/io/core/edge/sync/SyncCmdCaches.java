package me.java.library.io.core.edge.sync;

import com.google.common.cache.CacheBuilder;
import me.java.library.io.base.Terminal;
import me.java.library.utils.base.LocalCache;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  SyncCmdCaches
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
public class SyncCmdCaches extends LocalCache<Terminal, SyncCmdCache> {
    public SyncCmdCaches() {
        super(CacheBuilder.newBuilder()
                .maximumSize(100000L)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build());
    }
}
