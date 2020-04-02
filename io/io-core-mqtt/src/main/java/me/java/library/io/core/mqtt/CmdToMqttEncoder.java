package me.java.library.io.core.mqtt;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import me.java.library.io.Cmd;

import java.util.List;

/**
 * File Name             :  M2mEncoder
 *
 * @author :  sylar
 * Create                :  2019/12/21
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
@ChannelHandler.Sharable
public class CmdToMqttEncoder extends MessageToMessageEncoder<Cmd> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Cmd msg, List<Object> out) throws Exception {

    }
}
