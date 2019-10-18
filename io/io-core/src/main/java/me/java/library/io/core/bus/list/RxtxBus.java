package me.java.library.io.core.bus.list;

import me.java.library.io.core.bus.AbstractBus;
import me.java.library.io.core.bus.Bus;
import me.java.library.io.core.bus.BusType;

/**
 * File Name             :  RxtxBus
 *
 * @Author :  sylar
 * @Create :  2019-10-15
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
public class RxtxBus extends AbstractBus {
    @Override
    public BusType getBusType() {
        return BusType.RXTX;
    }

    public String getRxtxPath() {
        return getOrDefault(Bus.BUS_ATTR_RXTX_PATH, "/dev/ttyMT1");
    }

    public int getRxtxBaud() {
        return getOrDefault(Bus.BUS_ATTR_RXTX_BAUDRATE, 9600);
    }

    public int getRxtxDatabits() {
        return getOrDefault(Bus.BUS_ATTR_RXTX_DATABITS, 8);
    }

    public float getRxtxStopbits() {
        return getOrDefault(Bus.BUS_ATTR_RXTX_STOPBITS, 1F);
    }

    public String getRxtxParitybit() {
        return getOrDefault(Bus.BUS_ATTR_RXTX_PARITYBIT, "NONE");
    }

    public boolean getRxtxDTR() {
        return getOrDefault(Bus.BUS_ATTR_RXTX_DTR, false);
    }

    public boolean getRxtxRTS() {
        return getOrDefault(Bus.BUS_ATTR_RXTX_RTS, false);
    }
}
