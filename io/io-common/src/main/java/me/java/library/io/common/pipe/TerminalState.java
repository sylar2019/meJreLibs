package me.java.library.io.common.pipe;

import io.netty.channel.Channel;
import me.java.library.io.common.cmd.Terminal;

import java.net.InetSocketAddress;

/**
 * File Name             :  TerminalState
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
public class TerminalState {
    private Terminal terminal;
    private Channel channel;
    private boolean connected;
    private InetSocketAddress socketAddress;

    public TerminalState(Terminal terminal) {
        this.terminal = terminal;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }
}
