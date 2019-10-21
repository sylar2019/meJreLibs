package me.java.library.common.chain;

/**
 * File Name             :  ChainContainer
 *
 * @Author :  sylar
 * Create                :  2019-10-20
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
public interface ChainContainer {

    void start(Object... args);

    void stop();

    void onStarted();

    void onStopped();

    void onTaskCompleted(BaseTask task, Object result);

    void onTaskThrowable(BaseTask task, Throwable t);

    void onChainFinished(Context context);

}
