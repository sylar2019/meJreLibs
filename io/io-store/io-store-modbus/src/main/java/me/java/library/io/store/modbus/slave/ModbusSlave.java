package me.java.library.io.store.modbus.slave;

import java.util.List;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.modbus.slave.ModbusSlave
 * @createDate : 2020/8/4
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public interface ModbusSlave {

    List<SlaveImage> getImages();

    SlaveImage getImage(int slaveId);

    void addImage(SlaveImage image);

    boolean removeImage(int slaveId);
}
