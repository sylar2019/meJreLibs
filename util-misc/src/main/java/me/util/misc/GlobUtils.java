package me.util.misc;

import com.google.common.base.Joiner;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  GlobUtils
 * @CreateDate :  2017/11/08
 * @Description : glob 模式（globbing）也被称之为 shell 通配符 <p> http://www.linuxidc.com/Linux/2016-08/134192.htm
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
public class GlobUtils {
    public static String genGlobStr(List<String> items) {
        if (items == null || items.size() == 0) {
            return "*";
        } else {
            return "{" + Joiner.on(",").skipNulls().join(items) + "}";
        }
    }
}
