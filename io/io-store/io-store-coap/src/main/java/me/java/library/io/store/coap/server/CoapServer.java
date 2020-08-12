package me.java.library.io.store.coap.server;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.coap.server.CoapServer
 * @createDate : 2020/8/7
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public interface CoapServer {
    void addResources(ServerResource... resources);

    void removeResource(ServerResource resource);
}
