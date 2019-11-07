package me.java.library.io.core.edge.event;

import me.java.library.io.base.Terminal;
import me.java.library.io.core.pipe.Pipe;
import me.java.library.utils.base.guava.AbstractEvent;


/**
 * File Name             :  InboundCmdEvent
 *
 * @Author :  sylar
 * @Create :  2019-10-13
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class ConnectionChangedEvent extends AbstractEvent<Pipe, ConnectionChangedEvent.Connection> {

    public ConnectionChangedEvent(Pipe pipe, Connection connection) {
        super(pipe, connection);
    }

    public static class Connection {
        Terminal terminal;
        boolean isConnected;

        public Connection(Terminal terminal, boolean isConnected) {
            this.terminal = terminal;
            this.isConnected = isConnected;
        }

        public Terminal getTerminal() {
            return terminal;
        }

        public void setTerminal(Terminal terminal) {
            this.terminal = terminal;
        }

        public boolean isConnected() {
            return isConnected;
        }

        public void setConnected(boolean connected) {
            isConnected = connected;
        }
    }
}
