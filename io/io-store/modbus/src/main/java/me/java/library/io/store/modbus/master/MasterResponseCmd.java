package me.java.library.io.store.modbus.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.serotonin.modbus4j.msg.ModbusResponse;

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
public class MasterResponseCmd extends MasterCmd {
    private final static String ATTR_RESPONSE = "modbus-response";

    public MasterResponseCmd(ModbusResponse response) {
        FunctionType functionType = FunctionType.fromValue(response.getFunctionCode());
        setFunctionType(functionType);
        setResponse(response);
    }

    @JsonIgnore
    public ModbusResponse getResponse() {
        return getAttr(ATTR_RESPONSE);
    }

    public void setResponse(ModbusResponse response) {
        setAttr(ATTR_RESPONSE, response);
    }

}
