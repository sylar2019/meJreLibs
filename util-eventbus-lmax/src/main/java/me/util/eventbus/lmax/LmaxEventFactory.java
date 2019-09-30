package me.util.eventbus.lmax;

import com.lmax.disruptor.EventFactory;

/**
 * File Name             :  LmaxEventFactory
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
public class LmaxEventFactory implements EventFactory<LmaxEvent> {
    @Override
    public LmaxEvent newInstance() {
        return new LmaxEvent();
    }
}
