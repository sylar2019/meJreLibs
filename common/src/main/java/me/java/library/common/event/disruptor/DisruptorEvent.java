package me.java.library.common.event.disruptor;

import me.java.library.common.event.Event;

/**
 * File Name             :  DisruptorEvent
 *
 * @Author :  sylar
 * Create                :  2019-10-21
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
public final class DisruptorEvent implements Event<Object, Object> {

    Object source;
    Object Content;

    @Override
    public Object getSource() {
        return source;
    }

    @Override
    public Object getContent() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public void setContent(Object content) {
        Content = content;
    }
}
