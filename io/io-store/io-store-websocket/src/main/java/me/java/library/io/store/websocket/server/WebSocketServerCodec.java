package me.java.library.io.store.websocket.server;

import com.google.common.base.Preconditions;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import me.java.library.io.core.bus.Scheme;
import me.java.library.io.core.codec.AbstractCodecWithLogAndIdle;
import me.java.library.io.core.codec.InboundHandler;
import me.java.library.io.store.websocket.WebSocketCmdResolver;
import me.java.library.io.store.websocket.WebSocketDecoder;
import me.java.library.io.store.websocket.WebSocketEncoder;

import java.io.File;

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
    private WebSocketServerBus bus;
    private WebSocketCmdResolver webSocketCmdResolver;

    public WebSocketServerCodec(WebSocketCmdResolver webSocketCmdResolver) {
        this.webSocketCmdResolver = webSocketCmdResolver;
    }

    @Override
    public void initPipeLine(Channel channel) throws Exception {
        super.initPipeLine(channel);
        Preconditions.checkNotNull(bus);

        final SslHandler sslHandler = getSslHandler();
        if (sslHandler != null) {
            channel.pipeline().addLast(sslHandler);
        }

        //http解编码器
        channel.pipeline().addLast(new HttpServerCodec());
        //分块写数据，防止发送大文件时导致内存溢出
        channel.pipeline().addLast(new ChunkedWriteHandler());
        //将多个消息体进行聚合,参数是聚合字节的最大长度
        channel.pipeline().addLast(new HttpObjectAggregator(1024 * 8));
        //WebSocketServerProtocolHandler处理器可以处理了 webSocket 协议的握手请求处理，以及 Close、Ping、Pong控制帧的处理。
        //对于文本和二进制的数据帧需要我们自己处理。
        channel.pipeline().addLast(new WebSocketServerProtocolHandler(bus.getPath()));
        //业务层解码
        channel.pipeline().addLast(WebSocketDecoder.HANDLER_NAME, new WebSocketDecoder(webSocketCmdResolver));
        //加上默认的入站处理器 InboundCmdHandler
        channel.pipeline().addLast(InboundHandler.HANDLER_NAME, new InboundHandler());

        //out
        //业务层编码
        channel.pipeline().addLast(WebSocketEncoder.HANDLER_NAME, new WebSocketEncoder(webSocketCmdResolver));
    }

    public WebSocketServerBus getBus() {
        return bus;
    }

    public void setBus(WebSocketServerBus bus) {
        this.bus = bus;
    }

    private SslHandler getSslHandler() throws Exception {
        if (Scheme.wss.match(bus.getScheme())) {
            File keyCertChainFile = new File(bus.getSslCertFilePath());
            File keyFile = new File(bus.getSslKeyFilePath());

            SslContext sslCtx = SslContextBuilder
                    .forServer(keyCertChainFile, keyFile)
                    .build();
            return sslCtx.newHandler(channel.alloc());
        }
        return null;
    }

}
