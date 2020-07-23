package me.java.library.io.common.pipe;

import me.java.library.common.service.Serviceable;
import me.java.library.io.common.cmd.Cmd;
import me.java.library.io.common.cmd.Host;

import java.util.concurrent.TimeUnit;

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
     * 发送指令，异步接收回应指令
     *
     * @param request
     */
    void send(Cmd request);

    /**
     * 发送指令，同步接收回应指令。【按默认超时】
     *
     * @param request 待发送的请求指令
     * @return 回应指令
     * @throws Exception
     */
    Cmd syncSend(Cmd request) throws Exception;

    /**
     * 发送指令，同步接收回应指令
     *
     * @param request 待发送的请求指令
     * @param timeout 回应超时时间
     * @param unit    回应超时单位
     * @return 回应指令
     * @throws Exception
     */
    Cmd syncSend(Cmd request, long timeout, TimeUnit unit) throws Exception;

    /**
     * 发送指令，同步接收回应指令
     *
     * @param request  待发送的请求指令
     * @param timeout  回应超时时间
     * @param unit     回应超时单位
     * @param tryTimes 重试次数
     * @return 回应指令
     * @throws Exception
     */
    Cmd syncSend(Cmd request, long timeout, TimeUnit unit, int tryTimes) throws Exception;

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
