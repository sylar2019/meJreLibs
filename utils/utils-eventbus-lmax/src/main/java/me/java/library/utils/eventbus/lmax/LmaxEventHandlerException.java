package me.java.library.utils.eventbus.lmax;

import com.lmax.disruptor.ExceptionHandler;

/**
 * File Name             :  LmaxEventHandlerException
 *
 * @Author :  sylar
 * @Create :  2019-09-01
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
public class LmaxEventHandlerException implements ExceptionHandler<LmaxEvent> {

    @Override
    public void handleEventException(Throwable ex, long sequence, LmaxEvent event) {

    }

    @Override
    public void handleOnStartException(Throwable ex) {

    }

    @Override
    public void handleOnShutdownException(Throwable ex) {

    }
}
