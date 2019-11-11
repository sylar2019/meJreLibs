package me.java.library.io.core.bus;


import me.java.library.common.model.po.BaseEnum;

/**
 * File Name             :  BusType
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
public enum BusType implements BaseEnum {

    //@formatter:off
    TcpServer   (1,"TcpServer"),
    TcpClient   (2,"TcpClient"),
    UdpUnicast  (3,"UdpUnicast"),
    UdpBroadcast(4,"UdpBroadcast"),
    UdpMulticast(5,"UdpMulticast"),
    RXTX        (6,"Rxtx"),
    //@formatter:on
    ;

    private final int value;
    private final String name;

    BusType(int value, String name) {
        this.value = value;
        this.name = name;
    }


    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getValue() {
        return 0;
    }}
