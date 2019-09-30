package me.util.eventbus.lmax;

/**
 * File Name             :  LmaxEvent
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
public final class LmaxEvent {
    private LmaxEventContent lmaxEventContent;

    public LmaxEventContent getLmaxEventContent() {
        return lmaxEventContent;
    }

    public void setLmaxEventContent(LmaxEventContent lmaxEventContent) {
        this.lmaxEventContent = lmaxEventContent;
    }
}
