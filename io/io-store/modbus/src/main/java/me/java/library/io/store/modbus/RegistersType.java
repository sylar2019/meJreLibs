package me.java.library.io.store.modbus;

/**
 * File Name             :  RegistersType
 * 寄存器类型
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
public enum RegistersType {
    //@formatter:off
    /**
     * 开关量输入
     */
    DiscretesInput(1,true),
    /**
     * 开关量输出
     */
    Coils(1,false),
    /**
     * 数值输入（eg:模拟量）
     */
    InputRegisters(16,true),
    /**
     * 数值输出（eg:模拟量）
     */
    HoldingRegisters(16,false),
    //@formatter:on
    ;
    int bits;
    boolean onlyRead;

    private RegistersType(int bits, boolean onlyRead) {
        this.bits = bits;
        this.onlyRead = onlyRead;
    }

    public int getBits() {
        return bits;
    }

    public boolean isOnlyRead() {
        return onlyRead;
    }
}
