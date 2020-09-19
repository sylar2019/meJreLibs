package me.java.library.io.store.opc.cmd;

import com.google.common.collect.Sets;
import org.openscada.opc.lib.da.DataCallback;

import java.util.Set;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.opc.cmd.OpcStartSubscribeCmd
 * @createDate : 2020/7/24
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class OpcStartSubscribeCmd extends OpcCmd {

    /**
     * 更新周期，毫秒
     */
    private int period = 100;
    private Set<String> items = Sets.newHashSet();
    private DataCallback dataCallback;

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Set<String> getItems() {
        return items;
    }

    public void setItems(Set<String> items) {
        this.items = items;
    }

    public DataCallback getDataCallback() {
        return dataCallback;
    }

    public void setDataCallback(DataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }
}
