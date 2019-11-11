package me.java.library.utils.disruptor.event;

import java.io.Serializable;

/**
 * File Name             :  IEvent
 *
 * @author :  sylar
 * Create :  2019-10-02
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
public interface Event<Source, Content> extends Serializable {

    Source getSource();

    Content getContent();
}
