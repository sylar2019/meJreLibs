package me.java.library.io.core.edge.sync;

import com.google.common.cache.CacheBuilder;
import me.java.library.io.base.Terminal;
import me.java.library.common.service.LocalCacheService;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  SyncCmdCacheService
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
public class SyncCmdCacheService extends LocalCacheService<String, SyncBean> {

    private Terminal terminal;

    public SyncCmdCacheService(Terminal terminal) {
        super(CacheBuilder.newBuilder()
                .maximumSize(1000L)
                .expireAfterWrite(3, TimeUnit.MINUTES)
                .build());

        this.terminal = terminal;
    }

    public Terminal getTerminal() {
        return terminal;
    }
}
