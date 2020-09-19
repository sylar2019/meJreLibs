package me.java.library.io.store.websocket;

import me.java.library.io.store.websocket.client.WebSocketClientCodec;
import me.java.library.io.store.websocket.client.WebSocketClientParams;
import me.java.library.io.store.websocket.client.WebSocketClientPipe;
import me.java.library.io.store.websocket.server.WebSocketServerCodec;
import me.java.library.io.store.websocket.server.WebSocketServerParams;
import me.java.library.io.store.websocket.server.WebSocketServerPipe;

/**
 * File Name             :  WebSocketExpress
 *
 * @author :  sylar
 * Create                :  2020/7/22
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
public class WebSocketExpress {
    /**
     * 快速创建 WebSocketClientPipe
     *
     * @param url 服务器端资源url,
     *            url 构成:[scheme:][//host:port][path][?query][#fragment]
     *            如:  ws://127.0.0.1:8800/ws
     *            或:  wss://127.0.0.1:8800/ws
     * @return
     */
    public static WebSocketClientPipe client(String url) {
        WebSocketClientCodec codec = new WebSocketClientCodec(WebSocketCmdResolver.DEFAULT);
        return new WebSocketClientPipe(new WebSocketClientParams(url), codec);
    }


    /**
     * 快速创建 WebSocketServerPipe
     *
     * @param localPort
     * @param contextPath
     * @param isSsl
     * @return
     */
    public static WebSocketServerPipe server(int localPort, String contextPath, boolean isSsl) {
        WebSocketServerCodec codec = new WebSocketServerCodec(WebSocketCmdResolver.DEFAULT);
        return new WebSocketServerPipe(new WebSocketServerParams(localPort, contextPath, isSsl), codec);
    }

}
