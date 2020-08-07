package me.java.library.io.store.websocket;

import me.java.library.io.store.websocket.client.WebSocketClientBus;
import me.java.library.io.store.websocket.client.WebSocketClientCodec;
import me.java.library.io.store.websocket.client.WebSocketClientPipe;
import me.java.library.io.store.websocket.server.WebSocketServerBus;
import me.java.library.io.store.websocket.server.WebSocketServerCodec;
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
     * @param url         服务器端资源url,
     *                    url 构成:[scheme:][//host:port][path][?query][#fragment]
     *                    如:  ws://127.0.0.1:8800/ws
     *                    或:  wss://127.0.0.1:8800/ws
     * @param cmdResolver 指令编解码器
     * @return
     */
    public static WebSocketClientPipe client(String url, WebSocketCmdResolver cmdResolver) {
        WebSocketClientBus bus = new WebSocketClientBus();
        bus.setUrl(url);

        WebSocketClientCodec codec = new WebSocketClientCodec(cmdResolver);
        return new WebSocketClientPipe(bus, codec);
    }

    /**
     * 快速创建 WebSocketServerPipe
     *
     * @param url         服务器端资源url
     *                    url 构成:[scheme:][//host:port][path][?query][#fragment]
     *                    如:  ws://127.0.0.1:8800/ws
     *                    或:  wss://127.0.0.1:8800/ws
     * @param cmdResolver 指令编解码器
     * @return
     */
    public static WebSocketServerPipe server(String url, WebSocketCmdResolver cmdResolver) {
        WebSocketServerBus bus = new WebSocketServerBus();
        bus.setUrl(url);

        WebSocketServerCodec codec = new WebSocketServerCodec(cmdResolver);
        return new WebSocketServerPipe(bus, codec);
    }

}
