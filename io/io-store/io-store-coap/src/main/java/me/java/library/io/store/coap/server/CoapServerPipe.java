package me.java.library.io.store.coap.server;

import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.pipe.BasePipe;
import me.java.library.utils.base.ExceptionUtils;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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
public class CoapServerPipe extends BasePipe implements CoapServer {

    private Server server;

    public CoapServerPipe() {
        server = new Server();
        server.addEndpoints(true, false);
    }

    public CoapServerPipe(int port) {
        server = new Server(port);
        server.addEndpoints(true, false);
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

    @Override
    public void addResources(ServerResource... resources) {
        this.server.add(resources);
        Stream.of(resources).forEach(r -> r.setPipe(this));
    }

    @Override
    public void removeResource(ServerResource resource) {
        this.server.remove(resource);
    }
}
