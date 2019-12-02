package me.java.library.utils.base.algorithm;

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
@SuppressWarnings("UnstableApiUsage")
public class Md5Utils {

    public static String md5(String str) {

        HashCode hashCode = Hashing.sha256().hashString(str, Charsets.UTF_8);
        return hashCode.toString();
    }

}
