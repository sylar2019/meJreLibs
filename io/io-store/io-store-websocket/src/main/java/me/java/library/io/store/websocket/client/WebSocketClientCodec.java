package me.java.library.io.store.websocket.client;

import com.google.common.base.Preconditions;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.stream.ChunkedWriteHandler;
import me.java.library.io.core.codec.AbstractCodecWithLogAndIdle;
import me.java.library.io.core.codec.InboundHandler;
import me.java.library.io.store.websocket.WebSocketCmdResolver;
import me.java.library.io.store.websocket.WebSocketDecoder;
import me.java.library.io.store.websocket.WebSocketEncoder;

import javax.net.ssl.SSLException;
import java.net.URI;

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

    private final WebSocketCmdResolver webSocketCmdResolver;
    private WebSocketClientParams params;

    public WebSocketClientCodec(WebSocketCmdResolver webSocketCmdResolver) {
        this.webSocketCmdResolver = webSocketCmdResolver;
    }

    public void setParams(WebSocketClientParams params) {
        this.params = params;
    }

    @Override
    public void initPipeLine(Channel channel) throws Exception {
        super.initPipeLine(channel);
        Preconditions.checkNotNull(params);

        final SslHandler sslHandler = getSslHandler();
        if (sslHandler != null) {
            channel.pipeline().addLast(sslHandler);
        }

        //http解编码器
        channel.pipeline().addLast(new HttpClientCodec());
        //分块写数据，防止发送大文件时导致内存溢出
        channel.pipeline().addLast(new ChunkedWriteHandler());
        //将多个消息体进行聚合,参数是聚合字节的最大长度
        channel.pipeline().addLast(new HttpObjectAggregator(1024 * 8));
        channel.pipeline().addLast(WebSocketClientCompressionHandler.INSTANCE);
        channel.pipeline().addLast(WebSocketClientHandler.HANDLER_NAME, getWebSocketClientHandler());

        //业务层解码
        channel.pipeline().addLast(WebSocketDecoder.HANDLER_NAME, new WebSocketDecoder(webSocketCmdResolver));
        //加上默认的入站处理器 InboundCmdHandler
        channel.pipeline().addLast(InboundHandler.HANDLER_NAME, new InboundHandler());

        //out
        //业务层编码
        channel.pipeline().addLast(WebSocketEncoder.HANDLER_NAME, new WebSocketEncoder(webSocketCmdResolver));
    }

    private WebSocketClientHandler getWebSocketClientHandler() {
        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                URI.create(params.getUriPath()),
                WebSocketVersion.V13,
                null,
                true,
                new DefaultHttpHeaders());
        return new WebSocketClientHandler(handshaker);
    }

    private SslHandler getSslHandler() throws SSLException {
        if (params.isSsl()) {
            SslContext sslCtx = SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
            return sslCtx.newHandler(channel.alloc(), params.getRemoteHost(), params.getRemotePort());
        }
        return null;
    }
}
