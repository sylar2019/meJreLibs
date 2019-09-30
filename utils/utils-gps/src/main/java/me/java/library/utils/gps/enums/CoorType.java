package me.java.library.utils.gps.enums;

/**
 * @author :  sylar
 * @FileName :
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
public enum CoorType {
    /**
     * 国际
     */
    WGS84(0),
    /**
     * 国测局
     */
    GCJ02(1),
    /**
     * 百度
     */
    BD09LL(2);

    private int value;

    CoorType(int value) {
        this.value = value;
    }

    public static CoorType valueOf(int value) {
        switch (value) {

            case 0:
                return WGS84;
            case 1:
                return GCJ02;
            case 2:
                return BD09LL;
            default:
                return WGS84;

        }
    }

    public int getValue() {
        return value;
    }

}
