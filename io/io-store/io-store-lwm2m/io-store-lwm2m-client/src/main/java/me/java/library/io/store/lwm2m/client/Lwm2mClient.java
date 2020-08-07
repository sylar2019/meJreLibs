package me.java.library.io.store.lwm2m.client;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.lwm2m.client.Lwm2mClient
 * @createDate : 2020/8/4
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public interface Lwm2mClient {
    void createObject(int objectId);

    void deleteObject(int objectId);

    void update();
}
