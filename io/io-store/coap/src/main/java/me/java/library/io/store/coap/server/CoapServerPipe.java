package me.java.library.io.store.coap.server;

import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.pipe.BasePipe;
import me.java.library.utils.base.ExceptionUtils;
import org.eclipse.californium.core.server.resources.Resource;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  CoapServerPipe
 *
 * @author :  sylar
 * Create                :  2020/7/20
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public class CoapServerPipe extends BasePipe {

    private Server server;

    public CoapServerPipe() {
        this(false, true);
    }

    public CoapServerPipe(boolean tcp, boolean udp) {
        server = new Server();
        server.addEndpoints(udp, tcp);
    }

    @Override
    protected boolean onStart() throws Exception {
        server.start();
        return true;
    }

    @Override
    protected boolean onStop() throws Exception {
        if (server != null) {
            server.start();
            server = null;
        }
        return true;
    }

    @Override
    protected boolean onSend(Cmd request) throws Exception {
        ExceptionUtils.notSupportMethod();
        return true;
    }

    @Override
    protected Cmd onSyncSend(Cmd request, long timeout, TimeUnit unit) throws Exception {
        ExceptionUtils.notSupportMethod();
        return null;
    }

    public void add(Resource... resources) {
        if (server != null) {
            server.add(resources);
        }
    }

    public void remove(Resource resource) {
        if (server != null) {
            server.remove(resource);
        }
    }

}
