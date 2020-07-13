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

//    public static CmdNode incomingCmd(String code) {
//        return incomingCmd(code, CmdType.General);
//    }
//
//    public static CmdNode incomingCmd(String code, CmdType type) {
//        return new CmdNode(Terminal.REMOTE, Terminal.LOCAL, code, type);
//    }
//
//    public static CmdNode outcomingCmd(String code) {
//        return outcomingCmd(code, CmdType.General);
//    }
//
//    public static CmdNode outcomingCmd(String code, CmdType type) {
//        return new CmdNode(Terminal.LOCAL, Terminal.REMOTE, code, type);
//    }

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
