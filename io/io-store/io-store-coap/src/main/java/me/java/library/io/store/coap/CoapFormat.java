package me.java.library.io.store.coap;

import me.java.library.common.model.po.BaseEnum;

/**
 * File Name             :  CoapMethod
 *
 * @author :  sylar
 * Create                :  2020/7/17
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
public enum CoapFormat implements BaseEnum {
    //@formatter:off

    unknown         (-1, "unknown","???"),
    text            (0,"text/plain","text"),
    wlnk            (40, "application/link-format", "wlnk"),
    xml             (41, "application/xml", "xml"),
    bin             (42, "application/octet-stream", "bin"),
    xmpp            (46, "application/xmpp+xml", "xmpp"),
    exi             (47, "application/exi", "exi"),
    json            (50, "application/json", "json"),
    cbor            (60, "application/cbor", "cbor"),
    senml_json      (110, "application/senml+json", "json"),
    senml_cbor      (112, "application/senml+cbor", "cbor"),
    lwm2m_tlv       (11542, "application/vnd.oma.lwm2m+tlv", "tlv"),
    lwm2m_json      (11543, "application/vnd.oma.lwm2m+json", "json"),
    //@formatter:on
    ;

    int value;
    String name;
    String extension;

    CoapFormat(int value, String name, String extension) {
        this.value = value;
        this.name = name;
        this.extension = extension;
    }

    public static CoapFormat valueOf(int value) {
        for (CoapFormat coapFormat : CoapFormat.values()) {
            if (coapFormat.getValue() == value) {
                return coapFormat;
            }
        }

        throw new IllegalArgumentException("invalid CoapFormat");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getValue() {
        return value;
    }

    public String getExtension() {
        return extension;
    }
}
