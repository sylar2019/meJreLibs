package me.java.library.utils.event.disruptor;

import com.lmax.disruptor.ExceptionHandler;

/**
 * @author :  sylar
 * @FileName :  EventExceptionHandler
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
public class EventExceptionHandler implements ExceptionHandler<Event> {
    @Override
    public void handleEventException(Throwable ex, long sequence, Event event) {
        System.out.println(ex.getMessage());
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        System.out.println(ex.getMessage());
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        System.out.println(ex.getMessage());
    }

}
