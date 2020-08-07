package me.java.library.io.store.opc.cmd;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.opc.OpcRequestCmd
 * @createDate : 2020/7/24
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class OpcReadRequestCmd extends OpcCmd {
    private Set<String> items = Sets.newHashSet();

    public Set<String> getItems() {
        return items;
    }

    public void setItems(Set<String> items) {
        this.items = items;
    }
}
