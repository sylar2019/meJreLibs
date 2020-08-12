package me.java.library.io.store.modbus.master;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FutureCallback;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.msg.ModbusResponse;
import me.java.library.common.service.ConcurrentService;
import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.pipe.BasePipe;
import me.java.library.io.store.modbus.ModbusParams;
import me.java.library.io.store.modbus.cmd.MaskValue;
import me.java.library.io.store.modbus.cmd.ModbusRequestCmd;
import me.java.library.io.store.modbus.cmd.ModbusResponseCmd;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * File Name             :  ModbusServerPipe
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
public class ModbusMasterPipe extends BasePipe<ModbusParams> {
    private final ModbusMaster master;

    public ModbusMasterPipe(ModbusMaster master) {
        super(new ModbusParams());
        this.master = master;
    }

    @Override
    protected boolean onStart() throws Exception {
        master.init();
        return true;
    }

    @Override
    protected boolean onStop() throws Exception {
        master.destroy();
        return true;
    }

    @Override
    protected boolean onSend(Cmd request) throws Exception {
        return ConcurrentService.getInstance().postCallable(
                () -> syncSend(request),
                new FutureCallback<Cmd>() {
                    @Override
                    public void onSuccess(@Nullable Cmd result) {
                        onReceived(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        onException(t);
                    }
                }).isDone();
    }

    @Override
    protected Cmd onSyncSend(Cmd request, long timeout, TimeUnit unit) throws Exception {

        Preconditions.checkState(request instanceof ModbusRequestCmd);
        ModbusRequestCmd cmd = (ModbusRequestCmd) request;
        master.setTimeout((int) TimeUnit.MILLISECONDS.convert(timeout, unit));

        ModbusResponse response;
        switch (cmd.getFunctionType()) {
            case READ_COILS:
                response = MasterUtils.readCoil(master, cmd.getSlaveId(), cmd.getStart(), cmd.getLength());
                break;
            case READ_DISCRETE_INPUTS:
                response = MasterUtils.readDiscreteInput(master, cmd.getSlaveId(), cmd.getStart(), cmd.getLength());
                break;
            case READ_HOLDING_REGISTERS:
                response = MasterUtils.readHoldingRegisters(master, cmd.getSlaveId(), cmd.getStart(), cmd.getLength());
                break;
            case READ_INPUT_REGISTERS:
                response = MasterUtils.readInputRegisters(master, cmd.getSlaveId(), cmd.getStart(), cmd.getLength());
                break;
            case WRITE_COIL:
                boolean boolValue = cmd.getValue();
                response = MasterUtils.writeCoil(master, cmd.getSlaveId(), cmd.getStart(), boolValue);
                break;
            case WRITE_REGISTER:
                int intValue = cmd.getValue();
                response = MasterUtils.writeRegister(master, cmd.getSlaveId(), cmd.getStart(), intValue);
                break;
            case READ_EXCEPTION_STATUS:
                response = MasterUtils.readExceptionStatus(master, cmd.getSlaveId());
                break;
            case WRITE_COILS:
                boolean[] boolValues = cmd.getValue();
                response = MasterUtils.writeCoils(master, cmd.getSlaveId(), cmd.getStart(), boolValues);
                break;
            case WRITE_REGISTERS:
                short[] shortValues = cmd.getValue();
                response = MasterUtils.writeRegisters(master, cmd.getSlaveId(), cmd.getStart(), shortValues);
                break;
            case REPORT_SLAVE_ID:
                response = MasterUtils.reportSlaveId(master, cmd.getSlaveId());
                break;
            case WRITE_MASK_REGISTER:
                MaskValue maskValue = cmd.getValue();
                response = MasterUtils.writeMaskRegister(master, cmd.getSlaveId(), cmd.getStart(), maskValue.getAndMask(), maskValue.getOrMask());
                break;
            default:
                throw new IllegalArgumentException("invalid functionType of cmd");
        }

        if (response.isException()) {
            throw new Exception(response.getExceptionMessage());
        } else {
            return new ModbusResponseCmd(response);
        }
    }

    @Override
    protected void checkOnSend(Cmd request) {
        super.checkOnSend(request);

        Preconditions.checkState(request instanceof ModbusRequestCmd);
        ModbusRequestCmd cmd = (ModbusRequestCmd) request;
        Preconditions.checkNotNull(cmd.getFunctionType());
        Preconditions.checkState(cmd.getSlaveId() > 0);
        Preconditions.checkState(cmd.getStart() > 0);
        Preconditions.checkState(cmd.getLength() > 0);
    }
}
