package me.java.library.io.common.pipe;

import me.java.library.common.service.Serviceable;
import me.java.library.io.common.cmd.Cmd;
import me.java.library.io.common.cmd.Host;

/**
 * File Name             :  Pipe
 *
 * @author :  sylar
 * Create :  2019-10-05
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
public interface Pipe extends Serviceable {

    /**
     * 启动
     */
    void start();

    /**
     * 停止
     */
    void stop();

    /**
     * 发送指令
     *
     * @param cmd
     */
    void send(Cmd cmd);

    /**
     * 是否在运行
     *
     * @return 运行状态
     */
    boolean isRunning();

    /**
     * 宿主主机信息
     *
     * @return
     */
    Host getHost();

    /**
     * 设置观察者
     *
     * @param watcher
     */
    void setWatcher(PipeWatcher watcher);

    /**
     * 设置是否启用守护线程
     *
     * @param enabled
     */
    void setDaemon(boolean enabled);

}
