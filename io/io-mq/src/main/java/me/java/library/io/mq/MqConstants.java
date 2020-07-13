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
public interface MqConstants {

    /**
     * 从dcs到dms的group前缀
     */
    String GROUP_DCS_TO_DMS = "group_iot_dcs_to_dms";

    /**
     * dms的group名称，固定
     */
    String GROUP_DMS_TO_DCS = "group_iot_dms_to_dcs";

    /**
     * 从dms到aps的group，固定
     */
    String GROUP_DMS_TO_APS = "group_iot_dms_to_aps";

    /**
     * 上行设备消息主题，从DCS到DMS
     */
    String TOPIC_DCS_TO_DMS = "topic_dcs_to_dms";

    /**
     * 下行设备消息主题，从DMS到DCS
     */
    String TOPIC_DMS_TO_DCS = "topic_dms_to_dcs";

    /**
     * 下行设备消息主题，从DMS到APS
     */
    String TOPIC_DMS_TO_APS = "topic_dms_to_aps";
}
