package me.java.library.io.common.bus;


import me.java.library.common.Attributable;

/**
 * File Name             :  Bus
 *
 * @author :  sylar
 * Create :  2019-09-05
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
public interface Bus extends Attributable {


    String BUS_ATTR_RXTX_PATH = "path";
    String BUS_ATTR_RXTX_BAUDRATE = "baudrate";
    String BUS_ATTR_RXTX_STOPBITS = "stopbits";
    String BUS_ATTR_RXTX_DATABITS = "Databits";
    String BUS_ATTR_RXTX_PARITYBIT = "Paritybit";
    String BUS_ATTR_RXTX_DTR = "DTR";
    String BUS_ATTR_RXTX_RTS = "RTS";

    BusType getBusType();

}
