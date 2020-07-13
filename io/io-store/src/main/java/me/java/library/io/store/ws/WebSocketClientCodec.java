package me.java.library.io.store.ws;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.stream.ChunkedWriteHandler;
import me.java.library.io.common.codec.AbstractCodecWithLogAndIdle;
import me.java.library.io.common.codec.Codec;
import me.java.library.io.common.codec.InboundCmdHandler;

import java.net.URI;
import java.util.LinkedHashMap;

/**
 * File Name             :  WSServerCodec
 *
 * @author :  sylar
 * Create :  2019-10-15
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
public class WebSocketClientCodec extends AbstractCodecWithLogAndIdle {

    String HANDLER_NAME_HTTP_CLIENT_CODEC = "httpClientCodec";
    String HANDLER_NAME_CHUNKED_WRITE = "chunkedWrite";
    String HANDLER_NAME_HTTP_OBJECT_AGGREGATOR = "httpObjectAggregator";
    String HANDLER_NAME_WS_CLIENT_PROTOCOL = "webSocketClientProtocolHandler";

    /**
     * WS服务端地址，类似："ws://localhost:8899/ws"
     */
    private String wsServerUrl;
    private WebSocketCmdResolver webSocketCmdResolver;

    public WebSocketClientCodec(String wsServerUrl, WebSocketCmdResolver webSocketCmdResolver) {
        this.wsServerUrl = wsServerUrl;
        this.webSocketCmdResolver = webSocketCmdResolver;
    }

    @Override
    protected void putHandlers(LinkedHashMap<String, ChannelHandler> handlers) {
        super.putHandlers(handlers);

        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                URI.create(wsServerUrl),
                WebSocketVersion.V13,
                null,
                false,
                new DefaultHttpHeaders());

        //http解编码器
        handlers.put(HANDLER_NAME_HTTP_CLIENT_CODEC, new HttpClientCodec());
        //分块写数据，防止发送大文件时导致内存溢出
        handlers.put(HANDLER_NAME_CHUNKED_WRITE, new ChunkedWriteHandler());
        //将多个消息体进行聚合,参数是聚合字节的最大长度
        handlers.put(HANDLER_NAME_HTTP_OBJECT_AGGREGATOR, new HttpObjectAggregator(1024 * 64));
        //WebSocketClientProtocolHandler
        handlers.put(HANDLER_NAME_WS_CLIENT_PROTOCOL, new WebSocketClientProtocolHandler(handshaker));

        //业务层解码
        handlers.put(Codec.HANDLER_NAME_SIMPLE_DECODER, new WebSocketDecoder(webSocketCmdResolver));
        //加上默认的入站处理器 InboundCmdHandler
        handlers.put(Codec.HANDLER_NAME_INBOUND_CMD, new InboundCmdHandler());

        //out
        //业务层编码
        handlers.put(Codec.HANDLER_NAME_SIMPLE_ENCODER, new WebSocketEncoder(webSocketCmdResolver));
    }

}
