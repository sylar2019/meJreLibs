package me.java.library.io.store.modbus.cmd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import me.java.library.io.store.modbus.FunctionType;

/**
 * File Name             :  MasterRequestCmd
 *
 * @author :  sylar
 * Create                :  2020/7/22
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public class ModbusRequestCmd extends ModbusCmd {
    private final static String ATTR_SLAVEID = "modbus-slaveId";
    private final static String ATTR_START = "modbus-start";
    private final static String ATTR_LENGTH = "modbus-length";
    private final static String ATTR_VALUE = "modbus-value";

    public ModbusRequestCmd(FunctionType functionType, int slaveId) {
        setFunctionType(functionType);
        setSlaveId(slaveId);
    }

    public ModbusRequestCmd(FunctionType functionType, int slaveId, int start, int length) {
        setFunctionType(functionType);
        setSlaveId(slaveId);
        setStart(start);
        setLength(length);
    }

    public void checkState() {
        FunctionType functionType = getFunctionType();
        Preconditions.checkNotNull(functionType, "invalid FunctionType");
        Preconditions.checkNotNull(getSlaveId(), "invalid slaveId");
        switch (functionType) {
            case REPORT_SLAVE_ID:
            case READ_EXCEPTION_STATUS:
                break;
            default:
                Preconditions.checkNotNull(getStart(), "invalid offset");
                Preconditions.checkNotNull(getLength(), "invalid length");
                break;
        }
    }

    @JsonIgnore
    public Integer getSlaveId() {
        return getAttr(ATTR_SLAVEID);
    }

    public void setSlaveId(Integer slaveId) {
        setAttr(ATTR_SLAVEID, slaveId);
    }

    @JsonIgnore
    public Integer getStart() {
        return getAttr(ATTR_START);
    }

    public void setStart(Integer start) {
        setAttr(ATTR_START, start);
    }

    @JsonIgnore
    public Integer getLength() {
        return getAttr(ATTR_LENGTH);
    }

    public void setLength(Integer length) {
        setAttr(ATTR_LENGTH, length);
    }

    @JsonIgnore
    public <V> V getValue() {
        return getAttr(ATTR_VALUE);
    }

    public void setValue(Object value) {
        setAttr(ATTR_VALUE, value);
    }
}
