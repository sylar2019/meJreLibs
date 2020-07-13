package me.java.library.io.common.cmd;

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

    Terminal UNKNOWN = new TerminalNode(UUID.randomUUID().toString(), "unknown");
    Terminal SERVER = new TerminalNode(UUID.randomUUID().toString(), "server");
    Terminal CLIENT = new TerminalNode(UUID.randomUUID().toString(), "client");
    Terminal LOCAL = new TerminalNode(UUID.randomUUID().toString(), "local");
    Terminal REMOTE = new TerminalNode(UUID.randomUUID().toString(), "remote");


    String getType();

    String ATTR_HOST = "host";
    String ATTR_PORT = "port";

    @JsonIgnore
    default InetSocketAddress getInetSocketAddress() {
        InetSocketAddress address = null;
        if (containsKey(ATTR_HOST) && containsKey(ATTR_PORT)) {
            try {
                String host = getAttr(ATTR_HOST);
                int port = getAttr(ATTR_PORT);
                if (host != null && port > 0) {
                    address = InetSocketAddress.createUnresolved(host, port);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return address;
    }

    default void setInetSocketAddress(InetSocketAddress address) {
        if (address != null) {
            setAttr(ATTR_HOST, address.getHostName());
            setAttr(ATTR_PORT, address.getPort());
        }
    }
}
