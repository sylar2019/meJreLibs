package me.java.library.io.store.coap;

import me.java.library.io.store.coap.client.CoapClientParams;
import me.java.library.io.store.coap.client.CoapClientPipe;
import me.java.library.io.store.coap.server.CoapServerParams;
import me.java.library.io.store.coap.server.CoapServerPipe;

/**
 * File Name             :  CoapExpress
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
public class CoapExpress {

    public static CoapServerPipe server() {
        return new CoapServerPipe(new CoapServerParams(false, true));
    }

    public static CoapServerPipe server(int port) {
        return new CoapServerPipe(new CoapServerParams(false, true, port));
    }

    public static CoapClientPipe client() {
        return new CoapClientPipe(new CoapClientParams());
    }
}
