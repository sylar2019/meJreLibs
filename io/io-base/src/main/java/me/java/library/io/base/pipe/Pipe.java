package me.java.library.io.base.pipe;

import me.java.library.common.service.Serviceable;
import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.cmd.Host;

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
     * 异步发送
     * 【关于异步发送】
     * 若有应答，应答可在 watcher 中接收
     *
     * @param request
     */
    void send(Cmd request);

    /**
     * 同步发送【按默认超时】
     * 【关于同步发送】
     * 一般情形下，通讯是以 请求/应答 配对形式进行
     * 在NIO、特殊物理总线、或复杂上层业务通讯协议下，请求/应答 不一定是按时间顺序的
     * 通常，请求/应答 的配对机制应由 业务协议定义
     * 配对机制详见 {@code SyncPairity}
     *
     * @param request 待发送的请求指令
     * @return 回应指令
     * @throws Exception
     */
    Cmd syncSend(Cmd request) throws Exception;

    /**
     * 同步发送
     *
     * @param request 待发送的请求指令
     * @param timeout 回应超时时间
     * @param unit    回应超时单位
     * @return 回应指令
     * @throws Exception
     */
    Cmd syncSend(Cmd request, long timeout, TimeUnit unit) throws Exception;

    /**
     * 同步发送
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
     * 通道是否在运行
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
