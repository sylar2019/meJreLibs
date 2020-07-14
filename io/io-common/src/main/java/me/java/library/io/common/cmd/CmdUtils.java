package me.java.library.io.common.cmd;

import com.google.common.base.Strings;

/**
 * File Name             :  CmdUtils
 *
 * @author :  sylar
 * Create :  2019-10-13
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class CmdUtils {

    public static boolean isValidCmd(Cmd cmd) {
        return cmd != null
                && !Strings.isNullOrEmpty(cmd.getCode())
                && isValidTerminal(cmd.getFrom())
                && isValidTerminal(cmd.getTo());
    }

    public static boolean isValidTerminal(Terminal terminal) {
        return terminal != null
                && !Strings.isNullOrEmpty(terminal.getType())
                && !Strings.isNullOrEmpty(terminal.getId());
    }
}
