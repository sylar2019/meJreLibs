package me.java.library.utils.disruptor.chain;

/**
 * File Name             :  ChainCallback
 *
 * @author :  sylar
 * Create :  2019-10-20
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
public interface ChainCallback {

    void onStarted();

    void onStopped();

    void onTaskCompleted(BaseTask task, Object result);

    void onTaskThrowable(BaseTask task, Throwable t);

    void onChainFinished(ChainContext context);

}

