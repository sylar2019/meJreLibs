package me.java.library.io.store.opc;

import me.java.library.io.store.opc.client.OpcClientParams;
import me.java.library.io.store.opc.client.OpcClientPipe;

/**
 * File Name             :  OpcExpress
 *
 * @author :  sylar
 * Create                :  2020/7/23
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
public class OpcExpress {
    public static OpcClientPipe client(OpcClientParams param) {
        return new OpcClientPipe(param);
    }
}
