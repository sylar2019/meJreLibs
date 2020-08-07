package me.java.library.io.store.coap.client;

import com.google.common.base.Preconditions;
import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.pipe.BasePipe;
import me.java.library.io.store.coap.CoapRequestCmd;
import me.java.library.io.store.coap.CoapResponseCmd;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.network.config.NetworkConfigDefaultHandler;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * File Name             :  CoapClientPipe
 *
 * @author :  sylar
 * Create                :  2020/7/17
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
public class CoapClientPipe extends BasePipe {

    private static final File CONFIG_FILE = new File("Californium.properties");
    private static final String CONFIG_HEADER = "Californium CoAP Properties file for Fileclient";
    private static final int DEFAULT_MAX_RESOURCE_SIZE = 2 * 1024 * 1024;
    private static final int DEFAULT_BLOCK_SIZE = 512;

    private static NetworkConfigDefaultHandler DEFAULTS = new NetworkConfigDefaultHandler() {

        @Override
        public void applyDefaults(NetworkConfig config) {
            config.setInt(NetworkConfig.Keys.MAX_RESOURCE_BODY_SIZE, DEFAULT_MAX_RESOURCE_SIZE);
            config.setInt(NetworkConfig.Keys.MAX_MESSAGE_SIZE, DEFAULT_BLOCK_SIZE);
            config.setInt(NetworkConfig.Keys.PREFERRED_BLOCK_SIZE, DEFAULT_BLOCK_SIZE);
            config.setInt(NetworkConfig.Keys.MULTICAST_BASE_MID, 65000);
        }
    };

    protected CoapClient client;

    public CoapClientPipe() {
        NetworkConfig config = NetworkConfig.createWithFile(CONFIG_FILE, CONFIG_HEADER, DEFAULTS);
        NetworkConfig.setStandard(config);
    }

    @Override
    protected boolean onStart() throws Exception {
        if (client == null) {
            client = new CoapClient();
        }
        return true;
    }

    @Override
    protected boolean onStop() throws Exception {
        if (client != null) {
            client.shutdown();
            client = null;
        }
        return true;
    }

    @Override
    protected boolean onSend(Cmd request) throws Exception {

        CoapHandler coapHandler = new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                onReceived(new CoapResponseCmd(response));
            }

            @Override
            public void onError() {
                onException(new TimeoutException());
            }
        };

        CoapRequestCmd req = (CoapRequestCmd) request;
        client.setURI(req.getUri());
        switch (req.getMethod()) {
            case GET:
                client.get(coapHandler);
                break;
            case PUT:
                client.put(coapHandler, req.getContent(), req.getFormat());
                break;
            case POST:
                client.post(coapHandler, req.getContent(), req.getFormat());
                break;
            case DELETE:
                client.delete(coapHandler);
                break;
            default:
                throw new IllegalArgumentException("invalid coap method");
        }
        return true;
    }

    @Override
    protected Cmd onSyncSend(Cmd request, long timeout, TimeUnit unit) throws Exception {
        CoapRequestCmd cmd = (CoapRequestCmd) request;
        client.setTimeout(TimeUnit.MILLISECONDS.convert(timeout, unit));
        client.setURI(cmd.getUri());

        CoapResponse response;
        switch (cmd.getMethod()) {
            case GET:
                response = client.get();
                break;
            case PUT:
                response = client.put(cmd.getContent(), cmd.getFormat());
                break;
            case POST:
                response = client.post(cmd.getContent(), cmd.getFormat());
                break;
            case DELETE:
                response = client.delete();
                break;
            default:
                throw new IllegalArgumentException("invalid coap method");
        }

        return new CoapResponseCmd(response);
    }

    @Override
    protected void checkOnSend(Cmd request) {
        super.checkOnSend(request);
        Preconditions.checkState(request instanceof CoapRequestCmd);

        CoapRequestCmd req = (CoapRequestCmd) request;
        Preconditions.checkNotNull(req.getUri());
        Preconditions.checkNotNull(req.getMethod());
    }
}
