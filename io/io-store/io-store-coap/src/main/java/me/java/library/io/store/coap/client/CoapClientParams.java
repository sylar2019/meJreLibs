package me.java.library.io.store.coap.client;

import me.java.library.io.base.pipe.BasePipeParams;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.network.config.NetworkConfigDefaultHandler;

import java.io.File;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.coap.client.CoapClientParams
 * @createDate : 2020/8/12
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class CoapClientParams extends BasePipeParams {
    private static final File CONFIG_FILE = new File("Californium.properties");
    private static final String CONFIG_HEADER = "Californium CoAP Properties file for Fileclient";
    private static final int DEFAULT_MAX_RESOURCE_SIZE = 2 * 1024 * 1024;
    private static final int DEFAULT_BLOCK_SIZE = 512;

    private static NetworkConfigDefaultHandler DEFAULTS = config -> {
        config.setInt(NetworkConfig.Keys.MAX_RESOURCE_BODY_SIZE, DEFAULT_MAX_RESOURCE_SIZE);
        config.setInt(NetworkConfig.Keys.MAX_MESSAGE_SIZE, DEFAULT_BLOCK_SIZE);
        config.setInt(NetworkConfig.Keys.PREFERRED_BLOCK_SIZE, DEFAULT_BLOCK_SIZE);
        config.setInt(NetworkConfig.Keys.MULTICAST_BASE_MID, 65000);
    };

    public CoapClientParams() {
        NetworkConfig config = NetworkConfig.createWithFile(CONFIG_FILE, CONFIG_HEADER, DEFAULTS);
        NetworkConfig.setStandard(config);
    }
}
