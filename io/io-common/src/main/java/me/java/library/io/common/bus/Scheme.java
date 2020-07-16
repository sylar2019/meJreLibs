package me.java.library.io.common.bus;

import java.util.Objects;

/**
 * File Name             :  Scheme
 *
 * @author :  sylar
 * Create                :  2020/7/16
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
public enum Scheme {

    //@formatter:off
    tcp,
    udp,
    http,
    https,
    ws,
    wss,
    mqtt,
    coap,
    //@formatter:on
    ;

    public boolean match(String text) {
        return Objects.equals(this, fromString(text));
    }

    public static Scheme fromString(String text) {
        if (text != null) {
            return Scheme.valueOf(text.toLowerCase());
        }
        return null;
    }

}
