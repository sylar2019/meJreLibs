package me.java.library.io.store.modbus.slave;

import com.serotonin.modbus4j.ModbusSlaveSet;
import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.pipe.BasePipe;
import me.java.library.io.store.modbus.ModbusParams;
import me.java.library.utils.base.ExceptionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class ModbusSlavePipe extends BasePipe<ModbusParams> implements ModbusSlave {

    private final ModbusSlaveSet slave;

    public ModbusSlavePipe(ModbusSlaveSet slave) {
        super(new ModbusParams());
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
    public List<SlaveImage> getImages() {
        return Stream.of(slave.getProcessImages().toArray())
                .filter(o -> o instanceof SlaveImage)
                .map(o -> (SlaveImage) o)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public SlaveImage getImage(int slaveId) {
        return (SlaveImage) slave.getProcessImage(slaveId);
    }

    @Override
    public void addImage(SlaveImage image) {
        slave.addProcessImage(image);
    }

    @Override
    public boolean removeImage(int slaveId) {
        return slave.removeProcessImage(slaveId);
    }

}
