package me.java.library.io.store.opc.cmd;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.opc.OpcRequestCmd
 * @createDate : 2020/7/24
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class OpcWriteResponseCmd extends OpcCmd {

    private int errorCount;

    private Map<String, Exception> result = Maps.newHashMap();

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public Map<String, Exception> getResult() {
        return result;
    }

    public void setResult(Map<String, Exception> result) {
        this.result = result;
    }
}
