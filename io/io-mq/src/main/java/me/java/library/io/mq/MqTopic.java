package me.java.library.io.mq;

/**
 * File Name             :  MqConstants
 *
 * @author :  sylar
 * Create                :  2019-10-23
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
public interface MqTopic {

    /**
     * 上行设备消息主题，从DCS到DMS
     */
    String DCS_TO_DMS = "iot_dcs_to_dms";

    /**
     * 下行设备消息主题，从DMS到DCS
     */
    String DMS_TO_DCS = "iot_dms_to_dcs";

    /**
     * 上行设备消息主题，从DMS到APS
     */
    String DMS_TO_APS = "iot_dms_to_aps";

    /**
     * 下行设备消息主题，从APS到DMS
     */
    String APS_TO_DMS = "iot_aps_to_dms";
}
