package me.java.library.io.store.modbus.slave;

import com.serotonin.modbus4j.ModbusSlaveSet;
import com.serotonin.modbus4j.ProcessImage;
import me.java.library.common.service.ConcurrentService;
import me.java.library.io.common.cmd.Cmd;
import me.java.library.io.common.pipe.AbstractPipe;
import me.java.library.utils.base.ExceptionUtils;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  ModbusClientPipe
 *
 * @author :  sylar
 * Create                :  2020/7/20
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
public class ModbusSlavePipe extends AbstractPipe {

    private ModbusSlaveSet slave;

    public ModbusSlavePipe(ModbusSlaveSet slave) {
        this.slave = slave;
    }

    public void add(ProcessImage processImage) {
        slave.addProcessImage(processImage);
    }

    public boolean removeProcessImage(int slaveId) {
        return slave.removeProcessImage(slaveId);
    }

    public boolean removeProcessImage(ProcessImage processImage) {
        return slave.removeProcessImage(processImage);
    }

    public ProcessImage getProcessImage(int slaveId) {
        return slave.getProcessImage(slaveId);
    }

    @Override
    protected void onStart() throws Exception {
        ConcurrentService.getInstance().postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    slave.start();
                    onPipeRunningChanged(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStop() throws Exception {
        slave.stop();
    }

    @Override
    protected void onSend(Cmd request) throws Exception {
        ExceptionUtils.notSupportMethod();
    }

    @Override
    protected Cmd onSyncSend(Cmd request, long timeout, TimeUnit unit) throws Exception {
        ExceptionUtils.notSupportMethod();
        return null;
    }
}
