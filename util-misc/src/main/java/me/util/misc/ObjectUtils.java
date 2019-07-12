package me.util.misc;

/**
 * File Name             :  ObjectUtils
 *
 * @author :  zhangyq
 * @create :  2018/11/22
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 ************************************************************/
public class ObjectUtils {
    public static Long toLong(Object o) {
        return o == null ? null : Long.valueOf(String.valueOf(o));
    }

    public static Float toFloat(Object o) {
        return o == null ? 0.0F : Float.valueOf(String.valueOf(o));
    }

    public static String toString(Object o) {
        return o == null ? null : String.valueOf(o);
    }

    public static Integer toInteger(Object o) {
        return o == null ? null : Integer.valueOf(String.valueOf(o));
    }

    public static Boolean toBoolean(Object o) {
        return o == null ? null : Boolean.valueOf(String.valueOf(o));
    }
}
