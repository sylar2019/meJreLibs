package me.java.library.io.store.modbus;

import com.serotonin.modbus4j.serial.SerialPortWrapper;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import me.java.library.utils.rxtx.RxtxParam;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * File Name             :  SerialPortWrapperImpl
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
public class SerialPortWrapperImpl implements SerialPortWrapper, SerialPortEventListener {
    private RxtxParam parameters;
    private InputStream inputStream;
    private OutputStream outputStream;
    private SerialPort serialPort;

    public SerialPortWrapperImpl(RxtxParam parameters) {
        this.parameters = parameters;
    }

    @Override
    public void open() throws Exception {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(parameters.getCommPortId());
            if (portIdentifier.isCurrentlyOwned()) {
                System.out.println("Error: Port is currently in use");
            } else if (portIdentifier.getPortType() == 1) {
                serialPort = (SerialPort) portIdentifier.open(parameters.getCommPortId(), 1000);
                serialPort.setSerialPortParams(parameters.getBaudRate(),
                        parameters.getBaudRate(),
                        parameters.getStopBits(),
                        parameters.getParity());

                serialPort.setFlowControlMode(parameters.getFlowControlMode());
                serialPort.setDTR(parameters.isDTR());
                serialPort.setRTS(parameters.isRTS());

                inputStream = serialPort.getInputStream();
                outputStream = serialPort.getOutputStream();

                serialPort.addEventListener(this);
                serialPort.notifyOnDataAvailable(true);
            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void close() throws Exception {
        if (serialPort == null) {
            return;
        }
        try {
            serialPort.notifyOnDataAvailable(false);
            serialPort.removeEventListener();
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serialPort.close();
            serialPort = null;
            inputStream = null;
            outputStream = null;
        }
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public int getBaudRate() {
        return parameters.getBaudRate();
    }

    @Override
    public int getFlowControlIn() {
        return parameters.getFlowControlIn();
    }

    @Override
    public int getFlowControlOut() {
        return parameters.getFlowControlOut();
    }

    @Override
    public int getDataBits() {
        return parameters.getDataBits();
    }

    @Override
    public int getStopBits() {
        return parameters.getStopBits();
    }

    @Override
    public int getParity() {
        return parameters.getParity();
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.DATA_AVAILABLE:
                break;
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            default:
                break;
        }
    }
}
