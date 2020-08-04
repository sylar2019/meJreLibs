package me.java.library.io.store.mqtt.server;

import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.pipe.BasePipe;

import java.util.concurrent.TimeUnit;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.mqtt.server.MqttServerPipe
 * @createDate : 2020/8/4
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class MqttServerPipe extends BasePipe {
    @Override
    protected boolean onStart() throws Exception {
        return false;
    }

    @Override
    protected boolean onStop() throws Exception {
        return false;
    }

    @Override
    protected boolean onSend(Cmd request) throws Exception {
        return false;
    }

    @Override
    protected Cmd onSyncSend(Cmd request, long timeout, TimeUnit unit) throws Exception {
        return null;
    }
}
