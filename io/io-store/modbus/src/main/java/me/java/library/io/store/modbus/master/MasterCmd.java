package me.java.library.io.store.modbus.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.java.library.io.common.cmd.CmdNode;

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
public abstract class MasterCmd extends CmdNode {
    private final static String ATTR_FUNCTION_TYPE = "function_type";

    @JsonIgnore
    public FunctionType getFunctionType() {
        return getAttr(ATTR_FUNCTION_TYPE);
    }

    public void setFunctionType(FunctionType functionType) {
        setAttr(ATTR_FUNCTION_TYPE, functionType);
    }
}
