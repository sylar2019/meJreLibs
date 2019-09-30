package me.java.library.utils.base;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class RegularUtils {
    private final static String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" +
            "([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    private final static String HOME_PAGE_REGEX = "http://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*";
    private final static String MOBILE_REGEX = "^((13[0-9])|(14[0-9])|(17[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
    private final static String IP_REGEX = "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}" +
            "(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$";

    /**
     * @param 待验证的字符串
     * @return 如果是符合邮箱格式的字符串, 返回<b>true</b>,否则为<b>false</b>
     */
    public static boolean isEmail(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return false;
        }
        return match(EMAIL_REGEX, str);
    }

    /**
     * @param 待验证的字符串
     * @return 如果是符合网址格式的字符串, 返回<b>true</b>,否则为<b>false</b>
     */
    public static boolean isHomepage(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return false;
        }
        return match(HOME_PAGE_REGEX, str);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isMobileNO(String mobiles) {
        if (Strings.isNullOrEmpty(mobiles)) {
            return false;
        }
        return match(MOBILE_REGEX, mobiles);
    }

    public static boolean isIp(String ipAddress) {
        if (Strings.isNullOrEmpty(ipAddress)) {
            return false;
        }
        return match(IP_REGEX, ipAddress);
    }

    public static void test(String[] args) {
        String ipAddress = "15168370114";
        System.out.println(RegularUtils.isMobileNO(ipAddress));
    }
}
