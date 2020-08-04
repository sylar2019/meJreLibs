package me.java.library.io.store.modbus.slave;

import com.serotonin.modbus4j.ModbusSlaveSet;
import com.serotonin.modbus4j.ProcessImage;
import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.pipe.BasePipe;
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
public class ModbusSlavePipe extends BasePipe implements ModbusSlave {

    private ModbusSlaveSet slave;

    public ModbusSlavePipe(ModbusSlaveSet slave) {
        this.slave = slave;
    }

    @Override
    protected boolean onStart() throws Exception {
        slave.start();
        return true;
    }

    @Override
    protected boolean onStop() throws Exception {
        slave.stop();
        return true;
    }

    @Override
    protected boolean onSend(Cmd request) throws Exception {
        ExceptionUtils.notSupportMethod();
        return true;
    }

    @Override
    protected Cmd onSyncSend(Cmd request, long timeout, TimeUnit unit) throws Exception {
        ExceptionUtils.notSupportMethod();
        return null;
    }

    @Override
    public void addProcessImage(ProcessImage processImage) {
        slave.addProcessImage(processImage);
    }

    @Override
    public boolean removeProcessImage(ProcessImage processImage) {
        return slave.removeProcessImage(processImage);
    }

    @Override
    public boolean removeProcessImage(int slaveId) {
        return slave.removeProcessImage(slaveId);
    }

    @Override
    public ProcessImage getProcessImage(int slaveId) {
        return slave.getProcessImage(slaveId);
    }
}
