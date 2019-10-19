package me.java.library.io.core.bean;

import me.java.library.io.base.Terminal;
import me.java.library.utils.base.LocalCache;

import java.net.InetSocketAddress;

/**
 * File Name             :  TerminalCache
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
public class TerminalCache extends LocalCache<Terminal, InetSocketAddress> {

    private static TerminalCache instance = new TerminalCache();

    private TerminalCache() {
    }

    synchronized public static TerminalCache getInstance() {
        return instance;
    }
}
