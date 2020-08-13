package me.java.library.io.store.modbus.slave;

import com.serotonin.modbus4j.BasicProcessImage;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.modbus.slave.AbstractSlaveImage
 * @createDate : 2020/8/13
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class SlaveImage extends BasicProcessImage {
    public SlaveImage(int slaveId) {
        super(slaveId);
    }
}
