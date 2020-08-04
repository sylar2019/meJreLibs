package me.java.library.io.store.modbus.cmd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.serotonin.modbus4j.msg.ModbusResponse;
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
public class ModbusResponseCmd extends ModbusCmd {
    private final static String ATTR_RESPONSE = "modbus-response";

    public ModbusResponseCmd(ModbusResponse response) {
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
