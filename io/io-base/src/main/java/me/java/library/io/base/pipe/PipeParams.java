package me.java.library.io.base.pipe;

import me.java.library.io.base.cmd.Host;

import java.time.Duration;

/**
 * @author : sylar
 * @fullName : me.java.library.io.base.pipe.PipeParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public interface PipeParams {
    PipeParams DEFAULT = new PipeParams() {
    };

    /**
     * 宿主主机信息
     *
     * @return
     */
    default Host getHost() {
        return Host.LOCAL;
    }

    /**
     * 是否启用自动守护
     *
     * @return
     */
    default boolean isDaemon() {
        return true;
    }

    /**
     * 自动守护检查周期
     *
     * @return
     */
    default Duration getDaemonDaurtion() {
        return Duration.ofSeconds(5);
    }

    /**
     * 是否启用事件机制
     *
     * @return
     */
    default boolean isEventEnabled() {
        return true;
    }
}
