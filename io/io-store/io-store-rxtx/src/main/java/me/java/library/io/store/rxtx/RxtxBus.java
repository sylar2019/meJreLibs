package me.java.library.io.store.rxtx;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.java.library.io.core.bus.AbstractBus;
import me.java.library.io.core.bus.BusType;
import me.java.library.utils.rxtx.RxtxParam;

/**
 * File Name             :  RxtxBus
 *
 * @author :  sylar
 * Create :  2019-10-15
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
    final static String BUS_ATTR_RXTX_PARAM = "rxtx-param";

    @Override
    public BusType getBusType() {
        return BusType.RXTX;
    }

    @JsonIgnore
    public RxtxParam getRxtxParam() {
        return getAttr(BUS_ATTR_RXTX_PARAM);
    }

    public void setRxtxParam(RxtxParam param) {
        setAttr(BUS_ATTR_RXTX_PARAM, param);
    }
}
