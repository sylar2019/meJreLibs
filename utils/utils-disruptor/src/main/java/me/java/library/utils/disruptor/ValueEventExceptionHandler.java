package me.java.library.utils.disruptor;

import com.lmax.disruptor.ExceptionHandler;

/**
 * @author :  sylar
 * @FileName :  ValueEventExceptionHandler
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
public class ValueEventExceptionHandler implements ExceptionHandler {

    @Override
    public void handleEventException(Throwable ex, long sequence, Object event) {
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
