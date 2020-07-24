package me.java.library.io.core.bus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.URI;

/**
 * File Name             :  AbstractSocketBus
 *
 * @author :  sylar
 * Create :  2019-10-15
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
public abstract class AbstractSocketBus extends AbstractBus {

    public final static String BUS_ATTR_SOCKET_URL = "url";
    public final static String BUS_ATTR_SSL_CERT_FILE = "sslCertFile";
    public final static String BUS_ATTR_SSL_KEY_FILE = "sslKeyFile";
    public final static String BUS_ATTR_SOCKET_NETWORK_INTERFACE = "networkInterface";

    public final static String DEFAULT_HOST = "localhost";
    public final static String DEFAULT_MULTICAST_HOST = "239.255.27.1";
    public final static String DEFAULT_BROADCAST_HOST = "255.255.255.255";
    public final static String ANY_HOST = "0.0.0.0";

    private URI getUri() {
        return URI.create(getUrl());
    }

    /**
     * url的构成：
     * [scheme:][//authority][path][?query][#fragment]
     * 或
     * [scheme:][//host:port][path][?query][#fragment]
     * 即：authority = host:port
     *
     * @return
     */
    @JsonIgnore
    public String getUrl() {
        return getAttr(BUS_ATTR_SOCKET_URL);
    }

    public void setUrl(String url) {
        setAttr(BUS_ATTR_SOCKET_URL, url);
    }

    @JsonIgnore
    public String getScheme() {
        return getUri().getScheme();
    }

    @JsonIgnore
    public String getHost() {
        return getUri().getHost();
    }

    @JsonIgnore
    public int getPort() {
        return getUri().getPort();
    }

    @JsonIgnore
    public String getPath() {
        return getUri().getPath();
    }

    @JsonIgnore
    public String getSslCertFilePath() {
        return getAttr(BUS_ATTR_SSL_CERT_FILE);
    }

    public void setSslCertFilePath(String certFilePath) {
        setAttr(BUS_ATTR_SSL_CERT_FILE, certFilePath);
    }

    @JsonIgnore
    public String getSslKeyFilePath() {
        return getAttr(BUS_ATTR_SSL_KEY_FILE);
    }

    public void setSslKeyFilePath(String keyFilePath) {
        setAttr(BUS_ATTR_SSL_KEY_FILE, keyFilePath);
    }

}
