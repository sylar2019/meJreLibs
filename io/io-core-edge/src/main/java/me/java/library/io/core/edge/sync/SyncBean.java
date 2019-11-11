package me.java.library.io.core.edge.sync;

import com.google.common.util.concurrent.SettableFuture;
import me.java.library.io.Cmd;

/**
 * File Name             :  SyncBean
 *
 * @author :  sylar
 * Create :  2019-10-19
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
public class SyncBean {
    private final SettableFuture<Cmd> future;
    private Cmd request;
    private Cmd response;

    public SyncBean(Cmd request) {
        this.request = request;
        this.future = SettableFuture.create();
    }

    public Cmd getRequest() {
        return request;
    }

    public Cmd getResponse() {
        return response;
    }

    public void setResponse(Cmd response) {
        this.response = response;
    }

    public SettableFuture<Cmd> getFuture() {
        return future;
    }
}
