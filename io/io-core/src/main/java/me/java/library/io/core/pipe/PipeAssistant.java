package me.java.library.io.core.pipe;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import me.java.library.io.base.pipe.Pipe;
import me.java.library.io.core.utils.ChannelAttr;

import java.util.Map;

/**
 * File Name             :  PipeAssistant
 *
 * @author :  sylar
 * Create                :  2019-11-14
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
public class PipeAssistant {

    private final Map<Pipe, PipeContext> map;

    private PipeAssistant() {
        map = Maps.newConcurrentMap();
    }

    public static PipeAssistant getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void add(PipeContext pipeContext) {
        map.put(pipeContext.getPipe(), pipeContext);
    }

    public void remove(PipeContext pipeContext) {
        map.remove(pipeContext.getPipe());
    }

    public PipeContext getPipeContext(ChannelHandlerContext ctx) {
        return getPipeContext(ctx.channel());
    }

    public PipeContext getPipeContext(Channel channel) {
        Pipe pipe = ChannelAttr.get(channel, ChannelAttr.ATTR_PIPE);
        return getPipeContext(pipe);
    }

    public PipeContext getPipeContext(Pipe pipe) {
        return map.get(pipe);
    }

    private static class SingletonHolder {
        private static final PipeAssistant INSTANCE = new PipeAssistant();
    }

}
