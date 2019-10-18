package me.java.library.io.base.cmd;

import com.google.common.base.Strings;
import me.java.library.io.base.Cmd;
import me.java.library.io.base.Terminal;

/**
 * File Name             :  CmdUtils
 *
 * @Author :  sylar
 * @Create :  2019-10-13
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
                && isValidTerminal(cmd.getSender())
                && isValidTerminal(cmd.getRecipient());
    }

    public static boolean isValidTerminal(Terminal terminal) {
        return terminal != null
                && !Strings.isNullOrEmpty(terminal.getType())
                && !Strings.isNullOrEmpty(terminal.getId());
    }
}
