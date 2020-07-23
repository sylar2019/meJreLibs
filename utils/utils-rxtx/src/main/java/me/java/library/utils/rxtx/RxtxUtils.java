package me.java.library.utils.rxtx;

import com.google.common.base.Strings;
import me.java.library.utils.base.OSInfoUtils;
import me.java.library.utils.base.ShellUtils;

/**
 * File Name             :  RxtxUtils
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
public class RxtxUtils {

    public static void preparePermisson(String rxtxPath) {

        System.setProperty("gnu.io.rxtx.SerialPorts", rxtxPath);
        String cmd;
        ShellUtils.CommandResult cmdRes;

        if (OSInfoUtils.isLinux() || OSInfoUtils.isMacOS() || OSInfoUtils.isMacOSX()) {
            cmd = String.format("chmod 666 %s", rxtxPath);
            cmdRes = ShellUtils.execCommand(cmd, true, true);
            if (!Strings.isNullOrEmpty(cmdRes.errorMsg)) {
                throw new SecurityException("没有串口读写权限");
            }
        }

        if (OSInfoUtils.isLinux()) {
            cmd = String.format("chmod 777 %s", "/data/local/tmp");
            cmdRes = ShellUtils.execCommand(cmd, true, true);
            if (!Strings.isNullOrEmpty(cmdRes.errorMsg)) {
                throw new SecurityException("没有android root权限");
            }
        }
    }
}
