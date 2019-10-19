package me.java.library.io.core.edge.sync;

import com.google.common.cache.CacheBuilder;
import me.java.library.io.base.Terminal;
import me.java.library.utils.base.LocalCache;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  SyncBeanCache
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
public class SyncBeanCache extends LocalCache<String, SyncBean> {

    private Terminal terminal;

    public SyncBeanCache(Terminal terminal) {
        super(CacheBuilder.newBuilder()
                .maximumSize(1000L)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build());

        this.terminal = terminal;
    }

    public Terminal getTerminal() {
        return terminal;
    }
}
