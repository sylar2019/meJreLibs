package me.java.library.io.store.modbus.master;

import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.msg.*;

/**
 * File Name             :  MasterUtils
 *
 * @author :  sylar
 * Create                :  2020/7/22
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
public class MasterUtils {

    public static ReadCoilsResponse readCoil(ModbusMaster master, int slaveId, int start, int len) throws ModbusTransportException {
        ReadCoilsRequest request = new ReadCoilsRequest(slaveId, start, len);
        return (ReadCoilsResponse) master.send(request);
    }

    public static ReadDiscreteInputsResponse readDiscreteInput(ModbusMaster master, int slaveId, int start, int len) throws ModbusTransportException {
        ReadDiscreteInputsRequest request = new ReadDiscreteInputsRequest(slaveId, start, len);
        return (ReadDiscreteInputsResponse) master.send(request);
    }

    public static ReadHoldingRegistersResponse readHoldingRegisters(ModbusMaster master, int slaveId, int start, int len) throws ModbusTransportException {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, len);
        return (ReadHoldingRegistersResponse) master.send(request);
    }

    public static ReadInputRegistersResponse readInputRegisters(ModbusMaster master, int slaveId, int start, int len) throws ModbusTransportException {
        ReadInputRegistersRequest request = new ReadInputRegistersRequest(slaveId, start, len);
        return (ReadInputRegistersResponse) master.send(request);

    }

    public static WriteCoilResponse writeCoil(ModbusMaster master, int slaveId, int offset, boolean value) throws ModbusTransportException {
        WriteCoilRequest request = new WriteCoilRequest(slaveId, offset, value);
        return (WriteCoilResponse) master.send(request);
    }

    public static WriteRegisterResponse writeRegister(ModbusMaster master, int slaveId, int offset, int value) throws ModbusTransportException {
        WriteRegisterRequest request = new WriteRegisterRequest(slaveId, offset, value);
        return (WriteRegisterResponse) master.send(request);
    }

    public static ReadExceptionStatusResponse readExceptionStatus(ModbusMaster master, int slaveId) throws ModbusTransportException {
        ReadExceptionStatusRequest request = new ReadExceptionStatusRequest(slaveId);
        return (ReadExceptionStatusResponse) master.send(request);
    }

    public static ReportSlaveIdResponse reportSlaveId(ModbusMaster master, int slaveId) throws ModbusTransportException {
        ReportSlaveIdRequest request = new ReportSlaveIdRequest(slaveId);
        return (ReportSlaveIdResponse) master.send(request);
    }

    public static WriteCoilsResponse writeCoils(ModbusMaster master, int slaveId, int start, boolean[] values) throws ModbusTransportException {
        WriteCoilsRequest request = new WriteCoilsRequest(slaveId, start, values);
        return (WriteCoilsResponse) master.send(request);
    }

    public static WriteRegistersResponse writeRegisters(ModbusMaster master, int slaveId, int start, short[] values) throws ModbusTransportException {
        WriteRegistersRequest request = new WriteRegistersRequest(slaveId, start, values);
        return (WriteRegistersResponse) master.send(request);
    }

    public static WriteMaskRegisterResponse writeMaskRegister(ModbusMaster master, int slaveId, int offset, int and, int or) throws ModbusTransportException {
        WriteMaskRegisterRequest request = new WriteMaskRegisterRequest(slaveId, offset, and, or);
        return (WriteMaskRegisterResponse) master.send(request);
    }

}
