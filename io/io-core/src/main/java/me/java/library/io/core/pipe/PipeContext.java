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
    private Set<Channel> channels;
    private Set<Terminal> terminals;
    private Map<Terminal, TerminalState> terminalStateMap;

    public PipeContext(Pipe pipe) {
        this.pipe = pipe;
        channels = Sets.newConcurrentHashSet();
        terminals = Sets.newConcurrentHashSet();
        terminalStateMap = Maps.newConcurrentMap();
    }

    public Pipe getPipe() {
        return pipe;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public Set<Terminal> getTerminals() {
        return terminals;
    }

    public TerminalState getTerminalState(Terminal terminal) {
        return terminalStateMap.get(terminal);
    }

    public void setTerminalState(TerminalState terminalState) {
        terminalStateMap.put(terminalState.getTerminal(), terminalState);
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
