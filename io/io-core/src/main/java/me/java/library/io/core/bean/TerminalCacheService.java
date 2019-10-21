package me.java.library.io.core.bean;

import me.java.library.io.base.Terminal;
import me.java.library.common.service.LocalCacheService;

import java.net.InetSocketAddress;

/**
 * File Name             :  TerminalCacheService
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
public class TerminalCacheService extends LocalCacheService<Terminal, InetSocketAddress> {

    private static TerminalCacheService instance = new TerminalCacheService();

    private TerminalCacheService() {
    }

    synchronized public static TerminalCacheService getInstance() {
        return instance;
    }
}
