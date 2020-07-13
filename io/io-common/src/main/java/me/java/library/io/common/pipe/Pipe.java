package me.java.library.io.common.pipe;

import me.java.library.common.service.Serviceable;
import me.java.library.io.common.cmd.Cmd;
import me.java.library.io.common.bus.Bus;
import me.java.library.io.common.cmd.Host;
import me.java.library.io.common.codec.Codec;

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
public interface Pipe<B extends Bus, C extends Codec> extends Serviceable {

    /**
     * 启动
     */
    void start();

    /**
     * 停止
     */
    void stop();

    /**
     * 重启
     */
    void restart();

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
     * 获取观察者
     *
     * @return
     */
    PipeWatcher getWatcher();

    /**
     * 设置观察者
     *
     * @param watcher
     */
    void setWatcher(PipeWatcher watcher);

    long getDaemonSeconds();

    void setDaemonSeconds(long seconds);

    /**
     * 发送指令
     *
     * @param cmd
     */
    void send(Cmd cmd);


}
