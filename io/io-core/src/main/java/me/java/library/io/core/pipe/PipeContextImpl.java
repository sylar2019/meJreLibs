package me.java.library.io.core.pipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.netty.channel.Channel;
import me.java.library.io.base.cmd.Terminal;
import me.java.library.io.base.pipe.Pipe;
import me.java.library.io.core.utils.ChannelAttr;

import java.util.Map;
import java.util.Set;

/**
 * File Name             :  PipeContextImpl
 *
 * @author :  sylar
 * Create                :  2020/7/24
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
public class PipeContextImpl implements PipeContext {

    private Pipe pipe;
    private Channel initChannel;
    /**
     * 一个pipe可能有多个channel
     * 一个channel上可能连接多个terminal
     */
    private Map<Channel, Set<Terminal>> channelMap;
    /**
     * 一个terminal有且仅有与唯一的channel对应
     */
    private Map<Terminal, Channel> terminalMap;

    public PipeContextImpl(Pipe pipe) {
        this.pipe = pipe;
        channelMap = Maps.newConcurrentMap();
        terminalMap = Maps.newConcurrentMap();
    }

    @Override
    public Pipe getPipe() {
        return pipe;
    }

    @Override
    public void initChannel(Channel channel) {
        this.initChannel = channel;
        activeChannel(channel);
        PipeAssistant.getInstance().add(this);
    }

    @Override
    public void activeChannel(Channel channel) {
        channel.attr(ChannelAttr.ATTR_PIPE).set(pipe);
        channelMap.put(channel, Sets.newConcurrentHashSet());
    }

    @Override
    public void inactiveChannel(Channel channel) {
        channel.attr(ChannelAttr.ATTR_PIPE).set(null);
        disposeChannel(channel);
    }

    @Override
    public void addTerminal(Channel channel, Terminal terminal) {
        //先清除
        removeTerminal(terminal);

        //再添加
        terminalMap.put(terminal, channel);
        if (!containsChannel(channel)) {
            channelMap.put(channel, Sets.newConcurrentHashSet());
        }
        getTerminalsByChannel(channel).add(terminal);
    }

    @Override
    public void removeTerminal(Terminal terminal) {
        terminalMap.remove(terminal);
        channelMap.values().forEach(set -> set.remove(terminal));
    }

    @Override
    public boolean containsChannel(Channel channel) {
        return channelMap.containsKey(channel);
    }

    @Override
    public boolean containsTerminal(Terminal terminal) {
        return terminalMap.containsKey(terminal);
    }

    @Override
    public Set<Channel> getChannels() {
        return channelMap.keySet();
    }

    @Override
    public Set<Terminal> getTerminals() {
        return terminalMap.keySet();
    }

    @Override
    public Set<Terminal> getTerminalsByChannel(Channel channel) {
        return channelMap.get(channel);
    }

    @Override
    public Channel getChannel(Terminal terminal) {
        if (containsTerminal(terminal)) {
            return terminalMap.get(terminal);
        }
        return initChannel;
    }

    @Override
    public void dispose() {
        channelMap.clear();
        terminalMap.clear();
        PipeAssistant.getInstance().remove(this);
    }

    private void disposeChannel(Channel channel) {
        if (containsChannel(channel)) {
            channelMap.get(channel).forEach(terminal -> terminalMap.remove(terminal));
            channelMap.remove(channel);
        }
    }
}
