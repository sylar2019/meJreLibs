package me.java.library.io.store.ws;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.java.library.io.common.bus.AbstractSocketBus;

/**
 * File Name             :  WebSocketBus
 *
 * @author :  sylar
 * Create                :  2020/7/13
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
public abstract class WebSocketBus extends AbstractSocketBus {
    public final static String BUS_ATTR_WEBSOCKET_IS_SSL = "isSsl";

    public final static String SCHEME_WS = "ws";
    public final static String SCHEME_WSS = "wss";

    @JsonIgnore
    public boolean isSsl() {
        return getOrDefault(BUS_ATTR_WEBSOCKET_IS_SSL, false);
    }

    @JsonIgnore
    public String getUriPath() {
        return String.format("%s://%s:%d%s",
                isSsl() ? SCHEME_WS : SCHEME_WSS,
                getHost(),
                getPort(),
                getSocketPath());
    }
}
