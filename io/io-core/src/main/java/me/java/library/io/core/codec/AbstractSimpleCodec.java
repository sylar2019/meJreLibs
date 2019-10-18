package me.java.library.io.core.codec;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import me.java.library.io.core.Constants;

/**
 * File Name             :  AbstractSimpleCodec
 *
 * @Author :  sylar
 * @Create :  2019-10-14
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
public abstract class AbstractSimpleCodec extends AbstractCodec {

    public AbstractSimpleCodec() {
        super();
        handlers.put(Codec.HANDLER_NAME_IDLE_STATE, getIdleStateHandler());
        handlers.put(Codec.HANDLER_NAME_LOG, getLoggingHandler());
    }

    protected IdleStateHandler getIdleStateHandler() {
        String handlerKey = Codec.HANDLER_NAME_IDLE_STATE;
        int readerIdleTimeSeconds = getHandlerAttr(handlerKey, Codec.HANDLER_ATTR_READ_IDLE_TIME, 0);
        int writerIdleTimeSeconds = getHandlerAttr(handlerKey, Codec.HANDLER_ATTR_WRITE_IDLE_TIME, 0);
        int allIdleTimeSeconds = getHandlerAttr(handlerKey, Codec.HANDLER_ATTR_ALL_IDLE_TIME, 0);
        return new IdleStateHandler(readerIdleTimeSeconds,
                writerIdleTimeSeconds,
                allIdleTimeSeconds);
    }

    protected LoggingHandler getLoggingHandler() {
        String handlerKey = Codec.HANDLER_NAME_LOG;
        String strLevel = getHandlerAttr(handlerKey, Codec.HANDLER_ATTR_LOG_LEVEL, "INFO");
        LogLevel level = Enum.valueOf(LogLevel.class, strLevel);
        return new LoggingHandler(handlerKey, level);
    }
}
