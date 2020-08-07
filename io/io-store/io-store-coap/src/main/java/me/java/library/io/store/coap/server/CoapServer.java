package me.java.library.io.store.coap.server;

import org.eclipse.californium.core.server.resources.Resource;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.coap.server.CoapServer
 * @createDate : 2020/8/7
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public interface CoapServer {
    void addResources(Resource... resources);

    void removeResource(Resource resource);
}
