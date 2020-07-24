package me.java.library.io.base.cmd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.java.library.common.Attributable;
import me.java.library.common.Identifiable;

import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * File Name             :  Terminal
 *
 * @author :  sylar
 * Create :  2019-10-05
 * Description           : 通讯终端
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public interface Terminal extends Identifiable<String>, Attributable {
    String ATTR_SOCKET_ADDRESS = "socketAddress";

    Terminal UNKNOWN = new TerminalNode(UUID.randomUUID().toString(), "unknown");
    Terminal SERVER = new TerminalNode(UUID.randomUUID().toString(), "server");
    Terminal CLIENT = new TerminalNode(UUID.randomUUID().toString(), "client");
    Terminal LOCAL = new TerminalNode(UUID.randomUUID().toString(), "local");
    Terminal REMOTE = new TerminalNode(UUID.randomUUID().toString(), "remote");

    String getType();

    @JsonIgnore
    default InetSocketAddress getInetSocketAddress() {
        return getAttr(ATTR_SOCKET_ADDRESS);
    }

    default void setInetSocketAddress(InetSocketAddress address) {
        setAttr(ATTR_SOCKET_ADDRESS, address);
    }
}
