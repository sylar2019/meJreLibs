package me.java.library.io.store.ws;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import me.java.library.io.common.codec.AbstractCodecWithLogAndIdle;
import me.java.library.io.common.codec.Codec;
import me.java.library.io.common.codec.InboundCmdHandler;

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
public class WebSocketServerCodec extends AbstractCodecWithLogAndIdle {

    String HANDLER_NAME_HTTP_SERVER_CODEC = "httpServerCodec";
    String HANDLER_NAME_CHUNKED_WRITE = "chunkedWrite";
    String HANDLER_NAME_HTTP_OBJECT_AGGREGATOR = "httpObjectAggregator";
    String HANDLER_NAME_WS_SERVER_PROTOCOL = "webSocketServerProtocolHandler";

    private WebSocketCmdResolver webSocketCmdResolver;

    public WebSocketServerCodec(WebSocketCmdResolver webSocketCmdResolver) {
        this.webSocketCmdResolver = webSocketCmdResolver;
    }

    @Override
    protected void putHandlers(LinkedHashMap<String, ChannelHandler> handlers) {
        super.putHandlers(handlers);

        //http解编码器
        handlers.put(HANDLER_NAME_HTTP_SERVER_CODEC, new HttpServerCodec());
        //分块写数据，防止发送大文件时导致内存溢出
        handlers.put(HANDLER_NAME_CHUNKED_WRITE, new ChunkedWriteHandler());
        //将多个消息体进行聚合,参数是聚合字节的最大长度
        handlers.put(HANDLER_NAME_HTTP_OBJECT_AGGREGATOR, new HttpObjectAggregator(1024 * 64));
        //WebSocketServerProtocolHandler处理器可以处理了 webSocket 协议的握手请求处理，以及 Close、Ping、Pong控制帧的处理。对于文本和二进制的数据帧需要我们自己处理。
        handlers.put(HANDLER_NAME_WS_SERVER_PROTOCOL, new WebSocketServerProtocolHandler("/"));
        //业务层解码
        handlers.put(Codec.HANDLER_NAME_SIMPLE_DECODER, new WebSocketDecoder(webSocketCmdResolver));
        //加上默认的入站处理器 InboundCmdHandler
        handlers.put(Codec.HANDLER_NAME_INBOUND_CMD, new InboundCmdHandler());

        //out
        //业务层编码
        handlers.put(Codec.HANDLER_NAME_SIMPLE_ENCODER, new WebSocketEncoder(webSocketCmdResolver));
    }

}
