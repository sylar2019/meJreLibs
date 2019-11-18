package me.java.library.utils.base;

/**
 * File Name             :  OSInfoUtils
 *
 * @author :  sylar
 * Create                :  2019-11-13
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
public class OSInfoUtils {

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    private OSInfoUtils() {
    }

    public static boolean isLinux() {
        return OS_NAME.indexOf("linux") >= 0;
    }

    public static boolean isMacOS() {
        return OS_NAME.indexOf("mac") >= 0 && OS_NAME.indexOf("os") > 0 && OS_NAME.indexOf("x") < 0;
    }

    public static boolean isMacOSX() {
        return OS_NAME.indexOf("mac") >= 0 && OS_NAME.indexOf("os") > 0 && OS_NAME.indexOf("x") > 0;
    }

    public static boolean isWindows() {
        return OS_NAME.indexOf("windows") >= 0;
    }

    public static boolean isOS2() {
        return OS_NAME.indexOf("os/2") >= 0;
    }

    public static boolean isSolaris() {
        return OS_NAME.indexOf("solaris") >= 0;
    }

    public static boolean isSunOS() {
        return OS_NAME.indexOf("sunos") >= 0;
    }

    public static boolean isMPEiX() {
        return OS_NAME.indexOf("mpe/ix") >= 0;
    }

    public static boolean isHPUX() {
        return OS_NAME.indexOf("hp-ux") >= 0;
    }

    public static boolean isAix() {
        return OS_NAME.indexOf("aix") >= 0;
    }

    public static boolean isOS390() {
        return OS_NAME.indexOf("os/390") >= 0;
    }

    public static boolean isFreeBSD() {
        return OS_NAME.indexOf("freebsd") >= 0;
    }

    public static boolean isIrix() {
        return OS_NAME.indexOf("irix") >= 0;
    }

    public static boolean isDigitalUnix() {
        return OS_NAME.indexOf("digital") >= 0 && OS_NAME.indexOf("unix") > 0;
    }

    public static boolean isNetWare() {
        return OS_NAME.indexOf("netware") >= 0;
    }

    public static boolean isOSF1() {
        return OS_NAME.indexOf("osf1") >= 0;
    }

    public static boolean isOpenVMS() {
        return OS_NAME.indexOf("openvms") >= 0;
    }

    /**
     * 获取操作系统名字
     *
     * @return 操作系统名
     */
    public static OS getOS() {
        OS os;

        if (isAix()) {
            os = OS.AIX;
        } else if (isDigitalUnix()) {
            os = OS.Digital_Unix;
        } else if (isFreeBSD()) {
            os = OS.FreeBSD;
        } else if (isHPUX()) {
            os = OS.HP_UX;
        } else if (isIrix()) {
            os = OS.Irix;
        } else if (isLinux()) {
            os = OS.Linux;
        } else if (isMacOS()) {
            os = OS.Mac_OS;
        } else if (isMacOSX()) {
            os = OS.Mac_OS_X;
        } else if (isMPEiX()) {
            os = OS.MPEiX;
        } else if (isNetWare()) {
            os = OS.NetWare_411;
        } else if (isOpenVMS()) {
            os = OS.OpenVMS;
        } else if (isOS2()) {
            os = OS.OS2;
        } else if (isOS390()) {
            os = OS.OS390;
        } else if (isOSF1()) {
            os = OS.OSF1;
        } else if (isSolaris()) {
            os = OS.Solaris;
        } else if (isSunOS()) {
            os = OS.SunOS;
        } else if (isWindows()) {
            os = OS.Windows;
        } else {
            os = OS.Others;
        }
        return os;
    }
}
