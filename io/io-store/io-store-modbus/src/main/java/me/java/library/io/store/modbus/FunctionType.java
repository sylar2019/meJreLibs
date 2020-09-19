package me.java.library.io.store.modbus;

import com.serotonin.modbus4j.code.FunctionCode;

/**
 * File Name             :  FunctionType
 * <p>
 * <p>
 * 数据类型 | 字节类型 | 读写
 * :--- | :---: | :---:
 * Discretes Input　　 |　  位变量　    |     只读
 * Coils　　　　　　　　 |　  位变量　    |     读写
 * Input Registers　　 |　　16-bit整型  |　   只读
 * Holding Registers  |　  16-bit整型  |　   读写
 * ---
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
public enum FunctionType {
    //@formatter:off
    READ_COILS(FunctionCode.READ_COILS),
    READ_DISCRETE_INPUTS(FunctionCode.READ_DISCRETE_INPUTS),
    READ_HOLDING_REGISTERS(FunctionCode.READ_HOLDING_REGISTERS),
    READ_INPUT_REGISTERS(FunctionCode.READ_INPUT_REGISTERS),
    WRITE_COIL(FunctionCode.WRITE_COIL),
    WRITE_REGISTER(FunctionCode.WRITE_REGISTER),
    READ_EXCEPTION_STATUS(FunctionCode.READ_EXCEPTION_STATUS),
    WRITE_COILS(FunctionCode.WRITE_COILS),
    WRITE_REGISTERS(FunctionCode.WRITE_REGISTERS),
    REPORT_SLAVE_ID(FunctionCode.REPORT_SLAVE_ID),
    WRITE_MASK_REGISTER(FunctionCode.WRITE_MASK_REGISTER),
    //@formatter:on
    ;

    byte code;

    FunctionType(byte code) {
        this.code = code;
    }

    public static FunctionType fromValue(byte code) {
        for (FunctionType value : FunctionType.values()) {
            if (value.code == code) {
                return value;
            }
        }

        throw new IllegalArgumentException("invalid FunctionCode:" + FunctionCode.toString(code));
    }
}
