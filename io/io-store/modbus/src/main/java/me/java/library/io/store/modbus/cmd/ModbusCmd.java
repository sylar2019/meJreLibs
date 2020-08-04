package me.java.library.io.store.modbus.cmd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.java.library.io.base.cmd.CmdNode;
import me.java.library.io.store.modbus.FunctionType;

/**
 * File Name             :  MasterCmd
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
public abstract class ModbusCmd extends CmdNode {
    private final static String ATTR_FUNCTION_TYPE = "function_type";

    @JsonIgnore
    public FunctionType getFunctionType() {
        return getAttr(ATTR_FUNCTION_TYPE);
    }

    public void setFunctionType(FunctionType functionType) {
        setAttr(ATTR_FUNCTION_TYPE, functionType);
    }
}
