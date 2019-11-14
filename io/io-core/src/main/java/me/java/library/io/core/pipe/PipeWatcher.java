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

    /**
     * 宿主机状态变更事件
     *
     * @param host
     * @param isRunning
     */
    void onHostStateChanged(Host host, boolean isRunning);

    /**
     * 通道运行状态变更事件
     *
     * @param pipe
     * @param isRunning
     */
    void onPipeRunningChanged(Pipe pipe, boolean isRunning);

    /**
     * 终端设备连接状态变更事件
     *
     * @param pipe
     * @param terminal
     * @param isConnected
     */
    void onConnectionChanged(Pipe pipe, Terminal terminal, boolean isConnected);

    /**
     * 收到指令
     *
     * @param pipe
     * @param cmd
     */
    void onReceived(Pipe pipe, Cmd cmd);

    /**
     * 发生异常
     *
     * @param pipe
     * @param t
     */
    void onException(Pipe pipe, Throwable t);
}
