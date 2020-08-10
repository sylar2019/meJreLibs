package me.java.library.io.store.coap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Charsets;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

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
public class CoapRequestCmd extends CoapCmd {
    private final static String ATTR_URI = "coap-uri";
    private final static String ATTR_METHOD = "coap-method";
    private final static String ATTR_FORMAT = "coap-format";
    private final static String ATTR_CONTENT = "coap-content";

    public CoapRequestCmd(String uri, CoapMethod method, CoapFormat format) {
        this(uri, method);
        setFormat(format);
    }

    public CoapRequestCmd(String uri, CoapMethod method) {
        setUri(uri);
        setMethod(method);
    }

    @JsonIgnore
    public String getUri() {
        return getAttr(ATTR_URI);
    }

    /**
     * uri of coap server
     *
     * @param uri eg: {@code coap://ip:port/resourcePath}
     */
    public void setUri(String uri) {
        setAttr(ATTR_URI, uri);
    }

    @JsonIgnore
    public CoapMethod getMethod() {
        return getOrDefault(ATTR_METHOD, CoapMethod.GET);
    }

    public void setMethod(CoapMethod method) {
        setAttr(ATTR_METHOD, method);
    }

    @JsonIgnore
    public byte[] getContent() {
        return getAttr(ATTR_CONTENT);
    }

    public void setContent(byte[] content) {
        setAttr(ATTR_CONTENT, content);
    }

    @JsonIgnore
    public String getContentText() {
        byte[] bytes = getAttr(ATTR_CONTENT);
        return new String(bytes, Charsets.UTF_8);
    }

    public void setContent(String content) {
        setAttr(ATTR_CONTENT, content.getBytes(Charsets.UTF_8));
    }

    @JsonIgnore
    public CoapFormat getFormat() {
        return getOrDefault(ATTR_FORMAT, CoapFormat.json);
    }

    /**
     * MediaType of content
     *
     * @param format
     * @see MediaTypeRegistry
     */
    public void setFormat(CoapFormat format) {
        setAttr(ATTR_FORMAT, format);
    }

}
