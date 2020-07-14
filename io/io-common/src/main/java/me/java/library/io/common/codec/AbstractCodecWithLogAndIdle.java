package me.java.library.io.common.codec;

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

    @Override
    public void initPipeLine(Channel channel) throws Exception {
        super.initPipeLine(channel);
        channel.pipeline().addLast(Codec.HANDLER_NAME_IDLE_STATE, getIdleStateHandler());
        channel.pipeline().addLast(Codec.HANDLER_NAME_LOG, getLoggingHandler());
    }

    public LogLevel getLogLevel() {
        return getHandlerAttr(Codec.HANDLER_NAME_LOG, Codec.HANDLER_ATTR_LOG_LEVEL, LogLevel.INFO);
    }

    public void setLogLevel(LogLevel logLevel) {
        setHandlerAttr(Codec.HANDLER_NAME_LOG, Codec.HANDLER_ATTR_LOG_LEVEL, logLevel);
    }

    public int getReadIdleTime() {
        return getHandlerAttr(Codec.HANDLER_NAME_IDLE_STATE, Codec.HANDLER_ATTR_READ_IDLE_TIME, 0);
    }

    public void setReadIdleTime(int seconds) {
        setHandlerAttr(Codec.HANDLER_NAME_IDLE_STATE, Codec.HANDLER_ATTR_READ_IDLE_TIME, seconds);
    }

    public int getWriteIdleTime() {
        return getHandlerAttr(Codec.HANDLER_NAME_IDLE_STATE, Codec.HANDLER_ATTR_WRITE_IDLE_TIME, 0);
    }

    public void setWriteIdleTime(int seconds) {
        setHandlerAttr(Codec.HANDLER_NAME_IDLE_STATE, Codec.HANDLER_ATTR_WRITE_IDLE_TIME, seconds);
    }

    public int getAllIdleTime() {
        return getHandlerAttr(Codec.HANDLER_NAME_IDLE_STATE, Codec.HANDLER_ATTR_ALL_IDLE_TIME, 0);
    }

    public void setAllIdleTime(int seconds) {
        setHandlerAttr(Codec.HANDLER_NAME_IDLE_STATE, Codec.HANDLER_ATTR_ALL_IDLE_TIME, seconds);
    }

    protected IdleStateHandler getIdleStateHandler() {
        int readIdleTime = getReadIdleTime();
        int writeIdleTime = getWriteIdleTime();
        int allIdleTime = getAllIdleTime();
        return new IdleStateHandler(readIdleTime, writeIdleTime, allIdleTime);
    }

    protected LoggingHandler getLoggingHandler() {
        LogLevel logLevel = getLogLevel();
        return new LoggingHandler(Codec.HANDLER_NAME_LOG, logLevel);
    }
}
