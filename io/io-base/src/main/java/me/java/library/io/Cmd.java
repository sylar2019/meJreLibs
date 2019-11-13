package me.java.library.io;

import me.java.library.common.Attributable;
import me.java.library.common.Identifiable;

/**
 * File Name             :  Cmd
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
public interface Cmd extends Identifiable<String>, Attributable {

    /**
     * 发送方
     *
     * @return
     */
    Terminal getFrom();

    /**
     * 接收方
     *
     * @return
     */
    Terminal getTo();

    /**
     * 消息code
     *
     * @return
     */
    String getCode();

    /**
     * 消息类型
     *
     * @return
     */
    CmdType getType();

    /**
     * 发生时间
     *
     * @return
     */
    long getTime();

}
