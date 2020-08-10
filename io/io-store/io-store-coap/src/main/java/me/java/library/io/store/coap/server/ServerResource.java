package me.java.library.io.store.coap.server;

import me.java.library.io.base.pipe.Pipe;
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

    Pipe pipe;

    public ServerResource(String name) {
        super(name);
    }

    public ServerResource(String name, boolean visible) {
        super(name, visible);
    }

    public Pipe getPipe() {
        return pipe;
    }

    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    @Override
    public void handleRequest(Exchange exchange) {
        Optional.ofNullable(pipe)
                .flatMap(p -> Optional.ofNullable(p.getWatcher()))
                .ifPresent(w -> {
                    Optional.of(exchange)
                            .map(v -> new CoapExchange(exchange, this))
                            .ifPresent(v -> {
                                CoapRequestCmd reqCmd = fromExchange(exchange);
                                w.onReceived(pipe, reqCmd);
                            });
                });

        super.handleRequest(exchange);
    }

    CoapRequestCmd fromExchange(Exchange exchange) {
        String uri = exchange.getRequest().getURI();
        CoapMethod coapMethod = CoapMethod.valueOf(exchange.getRequest().getCode().toString());
        CoapFormat coapFormat = CoapFormat.valueOf(exchange.getRequest().getOptions().getContentFormat());

        CoapRequestCmd reqCmd = new CoapRequestCmd(uri, coapMethod, coapFormat);
        reqCmd.setContent(exchange.getRequest().getPayloadString());
        return reqCmd;
    }
}
