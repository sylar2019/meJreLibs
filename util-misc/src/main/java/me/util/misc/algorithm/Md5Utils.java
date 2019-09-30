package me.util.misc.algorithm;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

/**
 * @author :  sylar
 * @FileName :  Md5Utils
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @@CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
public class Md5Utils {

    public static String md5(String str) {

        HashCode hashCode = Hashing.md5().hashString(str, Charsets.UTF_8);
        String md5 = hashCode.toString();
        return md5;
    }

}
