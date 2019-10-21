package me.java.library.io.core.edge.sync;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.java.library.io.base.Cmd;
import me.java.library.io.base.Terminal;
import me.java.library.io.base.cmd.CmdUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * File Name             :  AbstractSyncPairity
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
public abstract class AbstractSyncPairity implements SyncPairity {

    /**
     * 【配对指令注册表】<K,V> 分别对应请求指令code与响应指令code
     */
    private BiMap<String, String> registy = HashBiMap.create();

    /**
     * 按Terminal缓存请求指令，当响应指令可配对为同步指令时，从缓存移除
     */
    private SyncCmdCachesService caches = new SyncCmdCachesService();

    public AbstractSyncPairity() {
        //初始构造【配对指令注册表】
        createReisty(registy);
    }

    protected abstract void createReisty(BiMap<String, String> registy);

    @Override
    public void cacheRequest(Cmd request) {
        Preconditions.checkState(CmdUtils.isValidCmd(request));
        Preconditions.checkState(isRegisted(request));

        SyncBean bean = new SyncBean(request);
        getCache(request.getTo()).put(request.getId(), bean);
    }

    @Override
    public boolean hasMatched(Cmd response) {
        Preconditions.checkState(CmdUtils.isValidCmd(response));
        if (!isRegisted(response)) {
            return false;
        }

        SyncCmdCacheService cache = getCache(response.getFrom());
        Cmd reqCmd = getMatchedRequest(cache, response);
        return reqCmd != null;
    }

    @Override
    public Cmd getResponse(Cmd request, long timeout, TimeUnit unit) throws Exception {
        Preconditions.checkState(CmdUtils.isValidCmd(request));
        Preconditions.checkState(isRegisted(request));

        SyncCmdCacheService cache = getCache(request.getTo());
        Preconditions.checkState(cache.containsKey(request.getId()));

        SyncBean bean = cache.get(request.getId());
        Cmd response = bean.getFuture().get(timeout, unit);
        cache.remove(request.getId());
        return response;
    }

    @Override
    public void cleanCache(Cmd request) {
        Preconditions.checkState(CmdUtils.isValidCmd(request));
        SyncCmdCacheService cache = getCache(request.getTo());
        if (cache.containsKey(request.getId())) {
            cache.remove(request.getId());
        }
    }

    protected Cmd getMatchedRequest(SyncCmdCacheService cache, Cmd response) {
        for (SyncBean bean : cache.getMap().values()) {
            Cmd request = bean.getRequest();
            if (pairs(cache.getTerminal(), request, response)) {
                bean.getFuture().set(response);
                return request;
            }
        }
        return null;
    }

    protected boolean pairs(Terminal terminal, Cmd request, Cmd response) {
        return pairs(registy, terminal, request, response);
    }

    protected boolean pairs(BiMap<String, String> map, Terminal terminal, Cmd request, Cmd response) {
        return Objects.equals(map.get(request.getCode()), response.getCode());
    }

    protected boolean isRegisted(Cmd cmd) {
        return isRegisted(registy, cmd);
    }

    protected boolean isRegisted(BiMap<String, String> map, Cmd cmd) {
        Preconditions.checkNotNull(map);
        Preconditions.checkState(CmdUtils.isValidCmd(cmd));
        return map.containsKey(cmd.getCode()) || map.inverse().containsKey(cmd.getCode());
    }

    protected SyncCmdCacheService getCache(Terminal terminal) {
        if (!caches.containsKey(terminal)) {
            SyncCmdCacheService cache = new SyncCmdCacheService(terminal);
            caches.put(terminal, cache);
        }
        return caches.get(terminal);
    }

}
