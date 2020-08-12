package me.java.library.io.store.lwm2m.server;

import me.java.library.io.base.pipe.BasePipeParams;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.lwm2m.server.Lwm2mServerParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class Lwm2mServerParams extends BasePipeParams {
    String webAddress;
    int webPort;
    String localAddress;
    String secureLocalAddress;
    Integer secureLocalPort;
    String modelsFolderPath;
    String redisUrl;
    PublicKey publicKey;
    PrivateKey privateKey;
    X509Certificate certificate;
    List<Certificate> trustStore;
    String keyStorePath;
    String keyStoreType;
    String keyStorePass;
    String keyStoreAlias;
    String keyStoreAliasPass;
    Boolean publishDnssdServices;
    boolean supportDeprecatedCiphers;

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

    public String getRedisUrl() {
        return redisUrl;
    }

    public void setRedisUrl(String redisUrl) {
        this.redisUrl = redisUrl;
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

    public String getKeyStorePath() {
        return keyStorePath;
    }

    public void setKeyStorePath(String keyStorePath) {
        this.keyStorePath = keyStorePath;
    }

    public String getKeyStoreType() {
        return keyStoreType;
    }

    public void setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
    }

    public String getKeyStorePass() {
        return keyStorePass;
    }

    public void setKeyStorePass(String keyStorePass) {
        this.keyStorePass = keyStorePass;
    }

    public String getKeyStoreAlias() {
        return keyStoreAlias;
    }

    public void setKeyStoreAlias(String keyStoreAlias) {
        this.keyStoreAlias = keyStoreAlias;
    }

    public String getKeyStoreAliasPass() {
        return keyStoreAliasPass;
    }

    public void setKeyStoreAliasPass(String keyStoreAliasPass) {
        this.keyStoreAliasPass = keyStoreAliasPass;
    }

    public Boolean getPublishDnssdServices() {
        return publishDnssdServices;
    }

    public void setPublishDnssdServices(Boolean publishDnssdServices) {
        this.publishDnssdServices = publishDnssdServices;
    }

    public boolean isSupportDeprecatedCiphers() {
        return supportDeprecatedCiphers;
    }

    public void setSupportDeprecatedCiphers(boolean supportDeprecatedCiphers) {
        this.supportDeprecatedCiphers = supportDeprecatedCiphers;
    }
}
