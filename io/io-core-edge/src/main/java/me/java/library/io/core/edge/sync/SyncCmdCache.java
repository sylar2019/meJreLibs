package me.java.library.io.core.edge.sync;

import com.google.common.cache.CacheBuilder;
import me.java.library.io.base.Terminal;
import me.java.library.utils.base.LocalCache;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  SyncCmdCache
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
public class SyncCmdCache extends LocalCache<String, SyncBean> {

    private Terminal terminal;

    public SyncCmdCache(Terminal terminal) {
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
