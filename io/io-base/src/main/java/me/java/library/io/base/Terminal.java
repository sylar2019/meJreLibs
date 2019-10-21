package me.java.library.io.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.java.library.common.Attributable;
import me.java.library.common.Identifiable;

import java.net.InetSocketAddress;

/**
 * File Name             :  Terminal
 *
 * @Author :  sylar
 * @Create :  2019-10-05
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
public interface Terminal extends Identifiable<String>, Attributable<String, Object> {

    Terminal LOCAL = new TerminalNode("local", "default");
    Terminal REMOTE = new TerminalNode("remote", "default");
    String ATTR_HOST = "host";
    String ATTR_PORT = "port";

    String getType();

    @JsonIgnore
    default InetSocketAddress getInetSocketAddress() {
        InetSocketAddress address = null;
        if (containsKey(ATTR_HOST) && containsKey(ATTR_PORT)) {
            try {
                String host = getAttr(ATTR_HOST) != null ? getAttr(ATTR_HOST).toString() : null;
                int port = getAttr(ATTR_HOST) != null ? Integer.parseInt(getAttr(ATTR_HOST).toString()) : 0;
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
