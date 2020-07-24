package me.java.library.io.store.modbus;

/**
 * File Name             :  MaskValue
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
public class MaskValue {

    private int andMask;
    private int orMask;

    public MaskValue(int andMask, int orMask) {
        this.andMask = andMask;
        this.orMask = orMask;
    }

    public int getAndMask() {
        return andMask;
    }

    public void setAndMask(int andMask) {
        this.andMask = andMask;
    }

    public int getOrMask() {
        return orMask;
    }

    public void setOrMask(int orMask) {
        this.orMask = orMask;
    }
}
