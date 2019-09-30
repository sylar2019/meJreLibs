package me.java.library.utils.eventbus.lmax;

import com.lmax.disruptor.EventHandler;

/**
 * File Name             :  BaseLmaxEventHandler
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
public abstract class BaseLmaxEventHandler implements EventHandler<LmaxEvent> {
    @Override
    public void onEvent(LmaxEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("接收到消息");
        this.onEvent(event.getLmaxEventContent());
    }

    abstract protected void onEvent(LmaxEventContent eventContent);
}
