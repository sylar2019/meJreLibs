package me.java.library.io.store.modbus;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.ModbusSlaveSet;
import com.serotonin.modbus4j.serial.SerialPortWrapper;
import me.java.library.utils.rxtx.RxtxParam;
import me.java.library.io.store.modbus.master.ModbusMasterPipe;
import me.java.library.io.store.modbus.slave.ModbusSlavePipe;

/**
 * File Name             :  MasterPipeFactory
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
public class ModebusExpress {

    static ModbusFactory factory = new ModbusFactory();

    public static ModbusMasterPipe rtuMaster(RxtxParam rxtxParam) {
        ModbusMaster master = factory.createRtuMaster(createWrapper(rxtxParam));
        return new ModbusMasterPipe(master);
    }

    public static ModbusMasterPipe asciiMaster(RxtxParam rxtxParam) {
        ModbusMaster master = factory.createAsciiMaster(createWrapper(rxtxParam));
        return new ModbusMasterPipe(master);
    }

    public static ModbusMasterPipe tcpListener(IpParam ipParam) {
        ModbusMaster master = factory.createTcpListener(ipParam.convert());
        return new ModbusMasterPipe(master);
    }

    public static ModbusMasterPipe tcpMaster(IpParam ipParam, boolean keepAlive) {
        ModbusMaster master = factory.createTcpMaster(ipParam.convert(), keepAlive);
        return new ModbusMasterPipe(master);
    }

    public static ModbusMasterPipe udpMaster(IpParam ipParam) {
        ModbusMaster master = factory.createUdpMaster(ipParam.convert());
        return new ModbusMasterPipe(master);
    }

    public static ModbusSlavePipe rtuSlave(RxtxParam rxtxParam) {
        ModbusSlaveSet slaveSet = factory.createRtuSlave(createWrapper(rxtxParam));
        return new ModbusSlavePipe(slaveSet);
    }

    public static ModbusSlavePipe asciiSlave(RxtxParam rxtxParam) {
        ModbusSlaveSet slaveSet = factory.createAsciiSlave(createWrapper(rxtxParam));
        return new ModbusSlavePipe(slaveSet);
    }

    public static ModbusSlavePipe tcpSlave(boolean encapsulated) {
        ModbusSlaveSet slaveSet = factory.createTcpSlave(encapsulated);
        return new ModbusSlavePipe(slaveSet);
    }

    public static ModbusSlavePipe udpSlave(boolean encapsulated) {
        ModbusSlaveSet slaveSet = factory.createUdpSlave(encapsulated);
        return new ModbusSlavePipe(slaveSet);
    }

    private static SerialPortWrapper createWrapper(RxtxParam rxtxParam) {
        return new SerialPortWrapperImpl(rxtxParam);
    }

}
