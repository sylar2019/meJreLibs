package me.java.library.io.base.pipe;

import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.cmd.Host;
import me.java.library.io.base.cmd.Terminal;

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
     * 【关于宿主机状态】
     * 原始涵义：在具备IaaS运维能力的环境下，通常是通过zabix等工具精确监控宿主机状态
     * 近似涵义：以应用进程的启停状态来近似表达
     *
     * @param host
     * @param isRunning
     */
    void onHostStateChanged(Host host, boolean isRunning);

    /**
     * 通道运行状态变更事件
     * 【关于通道运行状态】
     * 通道是本框架定义的一个逻辑概念，要区别与物理链路
     * 通道运行态为 true  : 通讯链路建立成功，但不代表通讯一定成功
     * 通道运行态为 false : 通讯链路已经拆除，通讯一定不成功
     *
     * @param pipe
     * @param isRunning
     */
    void onPipeRunningChanged(Pipe pipe, boolean isRunning);

    /**
     * 终端设备连接状态变更事件
     * 【关于连接状态】
     * <p>
     * 狭义概念：
     * tcp链路，可以明确连接状态
     * udp链路，实际上没有连接概念的
     * rxtx链路，实际上没有连接概念的
     * <p>
     * 广义概念：
     * 连接状态，通常是以上层通讯业务能正常请求应答为标准来定义
     * 故: 广义的连接状态应有上层业务同自定义。
     * 在非可靠通讯链路(如tcp)上，连接态有一些常用的判定规则：
     * 1、收到消息，立即判定为 true;
     * 2、多次发送请求且未收到应答的，可判定为 false;
     * 3、当本端为伺服端时，指定时间内没有收到终端上报的，可判定为 false;
     *
     * @param pipe
     * @param terminal
     * @param isConnected
     */
    void onConnectionChanged(Pipe pipe, Terminal terminal, boolean isConnected);

    /**
     * 收到指令
     * 异步方式收到消息指令，此时不能判定此消息是对端的应答还是主动上报，也无从判定与哪一个请求配对
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
