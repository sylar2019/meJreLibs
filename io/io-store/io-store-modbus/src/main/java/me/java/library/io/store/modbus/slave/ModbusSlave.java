package me.java.library.io.store.modbus.slave;

import com.serotonin.modbus4j.ProcessImage;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.modbus.slave.ModbusSlave
 * @createDate : 2020/8/4
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public interface ModbusSlave {

    void addProcessImage(ProcessImage processImage);

    boolean removeProcessImage(ProcessImage processImage);

    boolean removeProcessImage(int slaveId);

    ProcessImage getProcessImage(int slaveId);
}
