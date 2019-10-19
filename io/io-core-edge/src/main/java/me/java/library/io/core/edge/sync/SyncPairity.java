package me.java.library.io.core.edge.sync;

import me.java.library.io.base.Cmd;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  SyncPairity
 *
 * @Author :  sylar
 * @Create :  2019-10-18
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
public interface SyncPairity {

    void cacheRequest(Cmd request);

    boolean hasMatched(Cmd response);

    Cmd getResponse(Cmd request, long timeout, TimeUnit unit) throws Exception;

    void cleanCache(Cmd request);
}
