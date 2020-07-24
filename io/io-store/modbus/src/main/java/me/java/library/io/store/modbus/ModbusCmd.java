package me.java.library.io.store.modbus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.java.library.io.base.cmd.CmdNode;

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
