package me.java.library.io.store.lwm2m.bsserver;

import me.java.library.io.base.pipe.BasePipeParams;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.lwm2m.server.Lwm2mBSServerParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class Lwm2mBSServerParams extends BasePipeParams {

    String webAddress;
    int webPort = 8080;
    String localAddress;
    Integer localPort;
    String secureLocalAddress;
    Integer secureLocalPort;
    String modelsFolderPath;
    String configFilename = "data/bootstrap.json";
    boolean supportDeprecatedCiphers;
    PublicKey publicKey;
    PrivateKey privateKey;
    X509Certificate certificate;
    List<Certificate> trustStore;

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public int getWebPort() {
        return webPort;
    }

    public void setWebPort(int webPort) {
        this.webPort = webPort;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    @Override
    public Integer getLocalPort() {
        return localPort;
    }

    @Override
    public void setLocalPort(Integer localPort) {
        this.localPort = localPort;
    }

    public String getSecureLocalAddress() {
        return secureLocalAddress;
    }

    public void setSecureLocalAddress(String secureLocalAddress) {
        this.secureLocalAddress = secureLocalAddress;
    }

    public Integer getSecureLocalPort() {
        return secureLocalPort;
    }

    public void setSecureLocalPort(Integer secureLocalPort) {
        this.secureLocalPort = secureLocalPort;
    }

    public String getModelsFolderPath() {
        return modelsFolderPath;
    }

    public void setModelsFolderPath(String modelsFolderPath) {
        this.modelsFolderPath = modelsFolderPath;
    }

    public String getConfigFilename() {
        return configFilename;
    }

    public void setConfigFilename(String configFilename) {
        this.configFilename = configFilename;
    }

    public boolean isSupportDeprecatedCiphers() {
        return supportDeprecatedCiphers;
    }

    public void setSupportDeprecatedCiphers(boolean supportDeprecatedCiphers) {
        this.supportDeprecatedCiphers = supportDeprecatedCiphers;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

    public List<Certificate> getTrustStore() {
        return trustStore;
    }

    public void setTrustStore(List<Certificate> trustStore) {
        this.trustStore = trustStore;
    }
}
