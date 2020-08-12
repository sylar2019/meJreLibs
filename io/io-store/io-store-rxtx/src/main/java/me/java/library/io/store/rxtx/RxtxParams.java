package me.java.library.io.store.rxtx;

import me.java.library.io.base.pipe.PipeParams;
import me.java.library.utils.rxtx.RxtxParam;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.rxtx.RxtxPipePram
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class RxtxParams extends RxtxParam implements PipeParams {
    public RxtxParams() {
    }

    public RxtxParams(String commPortId) {
        super(commPortId);
    }

    public RxtxParams(String commPortId, int baudRate) {
        super(commPortId, baudRate);
    }

    public RxtxParams(String commPortId, int baudRate, int dataBits, int stopBits, int parity) {
        super(commPortId, baudRate, dataBits, stopBits, parity);
    }
}
