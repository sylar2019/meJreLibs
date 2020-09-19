package me.java.library.utils.rxtx;

import com.google.common.base.Preconditions;
import gnu.io.SerialPort;

/**
 * File Name             :  SerialPortParam
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
public class RxtxParam {
    private String commPortId = "COM1";
    private int baudRate = 9600;
    private int dataBits = SerialPort.DATABITS_8;
    private int stopBits = SerialPort.STOPBITS_1;
    private int parity = SerialPort.PARITY_NONE;
    private int flowControlMode = SerialPort.FLOWCONTROL_NONE;
    private int flowControlIn = SerialPort.FLOWCONTROL_RTSCTS_IN;
    private int flowControlOut = SerialPort.FLOWCONTROL_RTSCTS_OUT;
    private boolean DTR;
    private boolean RTS;

    public RxtxParam() {
        this("COM1");
    }

    public RxtxParam(String commPortId) {
        this(commPortId, 9600);
    }

    public RxtxParam(String commPortId, int baudRate) {
        this(commPortId,
                baudRate,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
    }


    public RxtxParam(String commPortId,
                     int baudRate,
                     int dataBits,
                     int stopBits,
                     int parity) {

        Preconditions.checkNotNull(commPortId);
        Preconditions.checkState(baudRate > 0);

        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
    }

    public String getCommPortId() {
        return commPortId;
    }

    public void setCommPortId(String commPortId) {
        this.commPortId = commPortId;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public void setDataBits(int dataBits) {
        this.dataBits = dataBits;
    }

    public int getStopBits() {
        return stopBits;
    }

    public void setStopBits(int stopBits) {
        this.stopBits = stopBits;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }

    public int getFlowControlMode() {
        return flowControlMode;
    }

    public void setFlowControlMode(int flowControlMode) {
        this.flowControlMode = flowControlMode;
    }

    public int getFlowControlIn() {
        return flowControlIn;
    }

    public void setFlowControlIn(int flowControlIn) {
        this.flowControlIn = flowControlIn;
    }

    public int getFlowControlOut() {
        return flowControlOut;
    }

    public void setFlowControlOut(int flowControlOut) {
        this.flowControlOut = flowControlOut;
    }

    public boolean isDTR() {
        return DTR;
    }

    public void setDTR(boolean DTR) {
        this.DTR = DTR;
    }

    public boolean isRTS() {
        return RTS;
    }

    public void setRTS(boolean RTS) {
        this.RTS = RTS;
    }
}
