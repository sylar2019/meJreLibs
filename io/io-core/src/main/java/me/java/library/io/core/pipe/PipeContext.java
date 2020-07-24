package me.java.library.io.core.pipe;

import io.netty.channel.Channel;
import me.java.library.io.base.cmd.Terminal;
import me.java.library.io.base.pipe.Pipe;

import java.util.Set;

/**
 * File Name             :  PipeContext
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
public interface PipeContext {

    Pipe getPipe();

    void initChannel(Channel channel);

    void activeChannel(Channel channel);

    void inactiveChannel(Channel channel);

    void addTerminal(Channel channel, Terminal terminal);

    void removeTerminal(Terminal terminal);

    boolean containsChannel(Channel channel);

    boolean containsTerminal(Terminal terminal);

    Set<Channel> getChannels();

    Set<Terminal> getTerminals();

    Set<Terminal> getTerminalsByChannel(Channel channel);

    Channel getChannel(Terminal terminal);

    void dispose();
}
