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

    public int getRxtxStopbits() {
        return getOrDefault(Bus.BUS_ATTR_RXTX_STOPBITS, 1);
    }

    public int getRxtxParitybit() {
        return getOrDefault(Bus.BUS_ATTR_RXTX_PARITYBIT, 0);
    }

    public boolean getRxtxDTR() {
        return getOrDefault(Bus.BUS_ATTR_RXTX_DTR, false);
    }

    public boolean getRxtxRTS() {
        return getOrDefault(Bus.BUS_ATTR_RXTX_RTS, false);
    }

    public void setRxtxBaud(int baud) {
        setAttr(Bus.BUS_ATTR_RXTX_BAUDRATE, baud);
    }

    public void setRxtxDatabits(int databits) {
        setAttr(Bus.BUS_ATTR_RXTX_DATABITS, databits);
    }

    public void setRxtxStopbits(int stopbits) {
        setAttr(Bus.BUS_ATTR_RXTX_STOPBITS, stopbits);
    }

    public void setRxtxParitybit(int paritybit) {
        setAttr(Bus.BUS_ATTR_RXTX_PARITYBIT, paritybit);
    }

    public void setRxtxDTR(boolean dtr) {
        setAttr(Bus.BUS_ATTR_RXTX_DTR, dtr);
    }

    public void setRxtxRTS(boolean rts) {
        setAttr(Bus.BUS_ATTR_RXTX_RTS, rts);
    }
}
