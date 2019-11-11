package me.java.library.utils.base;

import com.google.common.base.Preconditions;

/**
 * File Name             :  ExceptionUtils
 *
 * @author :  sylar
 * Create :  2019-05-28
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
public class ExceptionUtils {

    public static void notSupportMethod() {
        throwException("not supported method");
    }

    public static void throwException(String message) {
        Preconditions.checkState(false, message);
    }
}
