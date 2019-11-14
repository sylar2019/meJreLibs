package me.java.library.io.core.pipe;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.handler.timeout.IdleStateEvent;
import me.java.library.io.Cmd;
import me.java.library.io.Terminal;
import me.java.library.io.core.utils.ChannelAttr;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Set;

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

    synchronized public static PipeAssistant getInstance() {
        return new PipeAssistant();
    }

    Map<Pipe, PipeContext> pipeAssistants;

    private PipeAssistant() {
        pipeAssistants = Maps.newConcurrentMap();
    }

    public Pipe getPipe(Channel channel) {
        return ChannelAttr.get(channel, ChannelAttr.ATTR_PIPE);
    }

    public Channel getChannel(AbstractPipe pipe, Terminal terminal) {
        TerminalState state = pipeAssistants.get(pipe).getTerminalState(terminal);
        if (state != null && state.getChannel() != null) {
            return state.getChannel();
        } else {
            //非伺服器模式时
            return pipe.channel;
        }
    }


    public Set<Channel> getChannels(Pipe pipe) {
        return pipeAssistants.get(pipe).getChannels();
    }

    public Set<Terminal> getTerminals(Pipe pipe) {
        return pipeAssistants.get(pipe).getTerminals();
    }

    public TerminalState getTerminalState(Pipe pipe, Terminal terminal) {
        return pipeAssistants.get(pipe).getTerminalState(terminal);
    }

    public void setTerminalState(Pipe pipe, TerminalState terminalState) {
        pipeAssistants.get(pipe).setTerminalState(terminalState);
    }


    public InetSocketAddress getTerminalSocketAddress(Channel channel, Terminal terminal) {
        return getTerminalState(getPipe(channel), terminal).getSocketAddress();
    }

    public void setTerminalSocketAddress(Channel channel, Terminal terminal, InetSocketAddress address) {
        Pipe pipe = getPipe(channel);
        TerminalState state = getTerminalState(pipe, terminal);
        state.setSocketAddress(address);
    }


    public void onThrowable(Channel channel, Throwable throwable) {
        AbstractPipe pipe = getBasePipe(channel);
        //TODO
//        pipe.onReceived(cmd);
    }


    public void onReceived(Channel channel, Cmd cmd) {
        AbstractPipe pipe = getBasePipe(channel);
        pipe.onReceived(cmd);

        //TODO -> onConnectionChanged
    }


    public void onChannelIdle(Channel channel, IdleStateEvent idleStateEvent) {
        AbstractPipe pipe = getBasePipe(channel);
        //TODO -> onConnectionChanged
//        pipe.onConnectionChanged(terminal, connected);

    }

    public void onConnectionChanged(Channel channel, Terminal terminal, boolean connected) {
        AbstractPipe pipe = getBasePipe(channel);
        pipe.onConnectionChanged(terminal, connected);

        TerminalState state = getTerminalState(pipe, terminal);
        if (connected) {
            state.setChannel(channel);
            state.setConnected(true);
        } else {
            state.setChannel(channel);
            state.setConnected(false);
        }
    }


    private AbstractPipe getBasePipe(Channel channel) {
        Pipe pipe = ChannelAttr.get(channel, ChannelAttr.ATTR_PIPE);
        if (pipe instanceof AbstractPipe) {
            return (AbstractPipe) pipe;
        }
        throw new RuntimeException("不支持的pipe实现，pipe 须派生自 AbstractPipe");
    }

}
