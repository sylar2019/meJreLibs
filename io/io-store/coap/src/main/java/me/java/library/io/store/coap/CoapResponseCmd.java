package me.java.library.io.store.coap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;

/**
 * File Name             :  CoapCmdNode
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
public class CoapResponseCmd extends CoapCmd {
    private final static String ATTR_RESPONSE = "coap-response";

    public CoapResponseCmd(CoapResponse response) {
        setResponse(response);
    }

    @JsonIgnore
    public CoapResponse getResponse() {
        return getAttr(ATTR_RESPONSE);
    }

    public void setResponse(CoapResponse response) {
        setAttr(ATTR_RESPONSE, response);
    }

    public void prettyPrint() {
        if (getResponse() != null) {
            System.out.println(Utils.prettyPrint(getResponse()));
        }
    }

}
