package me.java.library.io.core.pipe;

import me.java.library.io.Cmd;
import me.java.library.io.Host;
import me.java.library.io.Terminal;

/**
 * File Name             :  PipeWatcher
 *
 * @author :  sylar
 * Create :  2019-10-14
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
public interface PipeWatcher {

    void onReceived(Pipe pipe, Cmd cmd);

    void onConnectionChanged(Pipe pipe, Terminal terminal, boolean isConnected);

    void onHostStateChanged(Host host, boolean isRunning);
}
