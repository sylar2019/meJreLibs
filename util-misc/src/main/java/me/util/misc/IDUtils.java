package me.util.misc;

import com.google.common.primitives.UnsignedInts;

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
public class IDUtils {

    private static byte[] KEY_ARRAY = {0x10, 0x21, 0x32, 0x43, 0x54, 0x65, 0x76, 0x11, 0x22, 0x33};

    public static long generateId(long id) {
        int idInt = (int) id;
        int userIdInt = Skip32Utils.encrypt(idInt, KEY_ARRAY);
        return UnsignedInts.toLong(userIdInt);
    }
}
