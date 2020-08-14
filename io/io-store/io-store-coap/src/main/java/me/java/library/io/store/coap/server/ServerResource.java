package me.java.library.io.store.coap.server;

import com.google.common.base.Charsets;
import me.java.library.io.store.coap.CoapFormat;
import me.java.library.io.store.coap.CoapMethod;
import me.java.library.io.store.coap.CoapRequestCmd;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.network.Exchange;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Optional;

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
        super.handleRequest(exchange);

        CoapExchange coapExchange = new CoapExchange(exchange, this);
        onReceivedCmd(coapExchange);
    }

    protected void onReceivedCmd(CoapExchange exchange) {
        CoapRequestCmd cmd = fromExchange(exchange);
        Optional.ofNullable(pipe).ifPresent(p -> p.onReceived(cmd));
    }

    protected CoapRequestCmd fromExchange(CoapExchange exchange) {
        String uri = exchange.getRequestOptions().getUriString();
        CoapMethod coapMethod = CoapMethod.valueOf(exchange.getRequestCode().toString());
        CoapFormat coapFormat = CoapFormat.valueOf(exchange.getRequestOptions().getContentFormat());

        CoapRequestCmd cmd = new CoapRequestCmd(uri, coapMethod, coapFormat);
        cmd.setContent(new String(exchange.getRequestPayload(), Charsets.UTF_8));
        return cmd;
    }
}
