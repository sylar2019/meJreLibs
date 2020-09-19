package me.java.library.io.core.pipe;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import me.java.library.io.base.cmd.Cmd;

/**
 * File Name             :  InboundKeeper
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
public interface ChannelKeeper {
    void onReceived(ChannelHandlerContext ctx, Cmd cmd);

    void onChannleActive(ChannelHandlerContext ctx);

    void onChannleInactive(ChannelHandlerContext ctx);

    void onIdleStateEvent(ChannelHandlerContext ctx, IdleStateEvent event);

    void onException(ChannelHandlerContext ctx, Throwable cause, boolean isInbound);
}
