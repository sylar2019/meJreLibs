package me.java.library.io.base;

import me.java.library.common.Attributable;
import me.java.library.common.IDPojo;
import me.java.library.io.base.cmd.CmdType;

/**
 * File Name             :  Cmd
 *
 * @Author :  sylar
 * @Create :  2019-10-05
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
public interface Cmd extends IDPojo<String>, Attributable<String, Object> {

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
     * 发生时间
     *
     * @return
     */
    long getTime();

    /**
     * 标签
     *
     * @return
     */
    Object getTag();

}
