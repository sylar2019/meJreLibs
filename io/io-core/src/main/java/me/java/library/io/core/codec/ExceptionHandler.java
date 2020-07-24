package me.java.library.io.core.codec;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.java.library.io.core.pipe.ChannelKeeper;

/**
 * File Name             :  ExceptionHandler
 *
 * @author :  sylar
 * Create                :  2020/7/23
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
public class ExceptionHandler extends ChannelDuplexHandler {
    public final static String HANDLER_NAME = ExceptionHandler.class.getSimpleName();

    private ChannelKeeper channelKeeper;

    public ChannelKeeper getChannelKeeper() {
        return channelKeeper;
    }

    public void setChannelKeeper(ChannelKeeper channelKeeper) {
        this.channelKeeper = channelKeeper;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg, promise.addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                onException(ctx, future.cause(), false);
            }
        }));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        onException(ctx, cause, true);
    }

    private void onException(ChannelHandlerContext ctx, Throwable cause, boolean isInbound) {
        if (channelKeeper != null) {
            channelKeeper.onException(ctx, cause, isInbound);
        }
    }
}
