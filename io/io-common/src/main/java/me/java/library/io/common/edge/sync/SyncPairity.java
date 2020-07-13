package me.java.library.io.common.edge.sync;

import me.java.library.io.common.cmd.Cmd;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  SyncPairity
 *
 * @author :  sylar
 * Create :  2019-10-18
 * Description           :  指令配对器
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public interface SyncPairity {

    void cacheRequest(Cmd request);

    void cleanCache(Cmd request);

    boolean hasMatched(Cmd response);

    Cmd getResponse(Cmd request, long timeout, TimeUnit unit) throws Exception;
}
