package me.java.library.io.core.pipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.netty.channel.Channel;
import me.java.library.io.Terminal;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * File Name             :  PipeContext
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
public class PipeContext {

    private Pipe pipe;

    private Map<Channel, Set<Terminal>> channelSetMap;
    private Map<Terminal, TerminalState> terminalStateMap;

    public PipeContext(Pipe pipe) {
        this.pipe = pipe;

        channelSetMap = Maps.newConcurrentMap();
        terminalStateMap = Maps.newConcurrentMap();
    }

    public Pipe getPipe() {
        return pipe;
    }

    public Set<Terminal> getTerminals(Channel channel) {
        if (!channelSetMap.containsKey(channel)) {
            channelSetMap.put(channel, Sets.newConcurrentHashSet());
        }
        return channelSetMap.get(channel);
    }

    public TerminalState getTerminalState(Terminal terminal) {
        if (!terminalStateMap.containsKey(terminal)) {
            terminalStateMap.put(terminal, new TerminalState(terminal));
        }
        return terminalStateMap.get(terminal);
    }

    @Override
    public int hashCode() {
        return pipe.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PipeContext) {
            PipeContext other = (PipeContext) obj;
            return Objects.equals(other.pipe, pipe);
        }

        return false;
    }
}
