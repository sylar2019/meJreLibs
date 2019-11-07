package me.java.library.utils.disruptor.chain;

import com.lmax.disruptor.EventHandler;

/**
 * File Name             :  BaseTask
 *
 * @Author :  sylar
 * @Create :  2019-10-20
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
public abstract class BaseTask implements EventHandler<ChainContext> {

    private ChainContainer container;

    public BaseTask(ChainContainer container) {
        this.container = container;
    }

    protected abstract Object onEvent(ChainContext context) throws Exception;

    public String getTaskCode() {
        return getClass().getSimpleName();
    }

    @Override
    public void onEvent(ChainContext event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("handler name: " + getClass().getSimpleName());
        try {
            Object result = this.onEvent(event);
            event.setTaskResult(getTaskCode(), result);
            container.onTaskCompleted(this, result);
        } catch (Exception e) {
            container.onTaskThrowable(this, e);
            throw e;
        }
    }

}
