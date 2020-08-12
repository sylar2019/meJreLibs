package me.java.library.io.store.lwm2m.client;

import me.java.library.io.base.pipe.BasePipeParams;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.lwm2m.client.Lwm2mClientParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class Lwm2mClientParams extends BasePipeParams {
    String endpoint;
    String localAddress;
    int localPort;
    boolean needBootstrap;
    Map<String, String> additionalAttributes;
    Map<String, String> bsAdditionalAttributes;
    int lifetime;
    Integer communicationPeriod;
    String serverUri;
    byte[] pskIdentity;
    byte[] pskKey;
    PrivateKey clientPrivateKey;
    PublicKey clientPublicKey;
    PublicKey serverPublicKey;
    X509Certificate clientCertificate;
    X509Certificate serverCertificate;
    boolean supportOldFormat;
    boolean supportDeprecatedCiphers;
    boolean reconnectOnUpdate;
    boolean forceFullhandshake;
    String modelsFolderPath;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    @Override
    public int getLocalPort() {
        return localPort;
    }

    @Override
    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public boolean isNeedBootstrap() {
        return needBootstrap;
    }

    public void setNeedBootstrap(boolean needBootstrap) {
        this.needBootstrap = needBootstrap;
    }

    public Map<String, String> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public Map<String, String> getBsAdditionalAttributes() {
        return bsAdditionalAttributes;
    }

    public void setBsAdditionalAttributes(Map<String, String> bsAdditionalAttributes) {
        this.bsAdditionalAttributes = bsAdditionalAttributes;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public Integer getCommunicationPeriod() {
        return communicationPeriod;
    }

    public void setCommunicationPeriod(Integer communicationPeriod) {
        this.communicationPeriod = communicationPeriod;
    }

    public String getServerUri() {
        return serverUri;
    }

    public void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }

    public byte[] getPskIdentity() {
        return pskIdentity;
    }

    public void setPskIdentity(byte[] pskIdentity) {
        this.pskIdentity = pskIdentity;
    }

    public byte[] getPskKey() {
        return pskKey;
    }

    public void setPskKey(byte[] pskKey) {
        this.pskKey = pskKey;
    }

    public PrivateKey getClientPrivateKey() {
        return clientPrivateKey;
    }

    public void setClientPrivateKey(PrivateKey clientPrivateKey) {
        this.clientPrivateKey = clientPrivateKey;
    }

    public PublicKey getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(PublicKey clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
    }

    public PublicKey getServerPublicKey() {
        return serverPublicKey;
    }

    public void setServerPublicKey(PublicKey serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

    public X509Certificate getClientCertificate() {
        return clientCertificate;
    }

    public void setClientCertificate(X509Certificate clientCertificate) {
        this.clientCertificate = clientCertificate;
    }

    public X509Certificate getServerCertificate() {
        return serverCertificate;
    }

    public void setServerCertificate(X509Certificate serverCertificate) {
        this.serverCertificate = serverCertificate;
    }

    public boolean isSupportOldFormat() {
        return supportOldFormat;
    }

    public void setSupportOldFormat(boolean supportOldFormat) {
        this.supportOldFormat = supportOldFormat;
    }

    public boolean isSupportDeprecatedCiphers() {
        return supportDeprecatedCiphers;
    }

    public void setSupportDeprecatedCiphers(boolean supportDeprecatedCiphers) {
        this.supportDeprecatedCiphers = supportDeprecatedCiphers;
    }

    public boolean isReconnectOnUpdate() {
        return reconnectOnUpdate;
    }

    public void setReconnectOnUpdate(boolean reconnectOnUpdate) {
        this.reconnectOnUpdate = reconnectOnUpdate;
    }

    public boolean isForceFullhandshake() {
        return forceFullhandshake;
    }

    public void setForceFullhandshake(boolean forceFullhandshake) {
        this.forceFullhandshake = forceFullhandshake;
    }

    public String getModelsFolderPath() {
        return modelsFolderPath;
    }

    public void setModelsFolderPath(String modelsFolderPath) {
        this.modelsFolderPath = modelsFolderPath;
    }
}
