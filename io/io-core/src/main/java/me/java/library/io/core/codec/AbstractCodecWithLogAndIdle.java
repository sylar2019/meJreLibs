package me.java.library.io.core.codec;

import io.netty.channel.Channel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * File Name             :  AbstractCodecWithLogAndIdle
 *
 * @author :  sylar
 * Create                :  2020/7/9
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AbstractCodecWithLogAndIdle extends AbstractCodec {

    final static String HANDLER_NAME_IDLE_STATE = "idleStateHandler";
    final static String HANDLER_NAME_LOG = "logHandler";

    final static String HANDLER_ATTR_LOG_LEVEL = "logLevel";
    final static String HANDLER_ATTR_READ_IDLE_TIME = "readerIdleTimeSeconds";
    final static String HANDLER_ATTR_WRITE_IDLE_TIME = "writerIdleTimeSeconds";
    final static String HANDLER_ATTR_ALL_IDLE_TIME = "allIdleTimeSeconds";

    @Override
    public void initPipeLine(Channel channel) throws Exception {
        super.initPipeLine(channel);
        channel.pipeline().addLast(HANDLER_NAME_IDLE_STATE, getIdleStateHandler());
        channel.pipeline().addLast(HANDLER_NAME_LOG, getLoggingHandler());
    }

    public LogLevel getLogLevel() {
        return getHandlerAttr(HANDLER_NAME_LOG, HANDLER_ATTR_LOG_LEVEL, LogLevel.INFO);
    }

    public void setLogLevel(LogLevel logLevel) {
        setHandlerAttr(HANDLER_NAME_LOG, HANDLER_ATTR_LOG_LEVEL, logLevel);
    }

    public int getReadIdleTime() {
        return getHandlerAttr(HANDLER_NAME_IDLE_STATE, HANDLER_ATTR_READ_IDLE_TIME, 0);
    }

    public void setReadIdleTime(int seconds) {
        setHandlerAttr(HANDLER_NAME_IDLE_STATE, HANDLER_ATTR_READ_IDLE_TIME, seconds);
    }

    public int getWriteIdleTime() {
        return getHandlerAttr(HANDLER_NAME_IDLE_STATE, HANDLER_ATTR_WRITE_IDLE_TIME, 0);
    }

    public void setWriteIdleTime(int seconds) {
        setHandlerAttr(HANDLER_NAME_IDLE_STATE, HANDLER_ATTR_WRITE_IDLE_TIME, seconds);
    }

    public int getAllIdleTime() {
        return getHandlerAttr(HANDLER_NAME_IDLE_STATE, HANDLER_ATTR_ALL_IDLE_TIME, 0);
    }

    public void setAllIdleTime(int seconds) {
        setHandlerAttr(HANDLER_NAME_IDLE_STATE, HANDLER_ATTR_ALL_IDLE_TIME, seconds);
    }

    protected IdleStateHandler getIdleStateHandler() {
        int readIdleTime = getReadIdleTime();
        int writeIdleTime = getWriteIdleTime();
        int allIdleTime = getAllIdleTime();
        return new IdleStateHandler(readIdleTime, writeIdleTime, allIdleTime);
    }

    protected LoggingHandler getLoggingHandler() {
        LogLevel logLevel = getLogLevel();
        return new LoggingHandler(HANDLER_NAME_LOG, logLevel);
    }
}
