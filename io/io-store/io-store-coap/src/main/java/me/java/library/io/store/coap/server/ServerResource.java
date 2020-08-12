package me.java.library.io.store.coap.server;

import me.java.library.io.store.coap.CoapFormat;
import me.java.library.io.store.coap.CoapMethod;
import me.java.library.io.store.coap.CoapRequestCmd;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.network.Exchange;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.coap.server.BaseResource
 * @createDate : 2020/8/10
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public abstract class ServerResource extends CoapResource {

    CoapServerPipe pipe;

    public ServerResource(String name) {
        super(name);
    }

    public ServerResource(String name, boolean visible) {
        super(name, visible);
    }

    public void setPipe(CoapServerPipe pipe) {
        this.pipe = pipe;
    }

    @Override
    public void handleRequest(Exchange exchange) {
        if (pipe != null) {
            CoapRequestCmd cmd = fromExchange(exchange);
            pipe.onReceived(cmd);
        }

        super.handleRequest(exchange);
    }

    CoapRequestCmd fromExchange(Exchange exchange) {
        String uri = exchange.getRequest().getURI();
        CoapMethod coapMethod = CoapMethod.valueOf(exchange.getRequest().getCode().toString());
        CoapFormat coapFormat = CoapFormat.valueOf(exchange.getRequest().getOptions().getContentFormat());

        CoapRequestCmd cmd = new CoapRequestCmd(uri, coapMethod, coapFormat);
        cmd.setContent(exchange.getRequest().getPayloadString());
        return cmd;
    }
}
