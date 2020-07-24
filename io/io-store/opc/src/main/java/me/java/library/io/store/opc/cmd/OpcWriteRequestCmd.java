package me.java.library.io.store.opc.cmd;

import com.google.common.collect.Maps;
import org.jinterop.dcom.core.JIVariant;

import java.util.Map;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.opc.OpcRequestCmd
 * @createDate : 2020/7/24
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class OpcWriteRequestCmd extends OpcCmd {
    private Map<String, JIVariant> values = Maps.newHashMap();

    public Map<String, JIVariant> getValues() {
        return values;
    }

    public void setValues(Map<String, JIVariant> values) {
        this.values = values;
    }
}
