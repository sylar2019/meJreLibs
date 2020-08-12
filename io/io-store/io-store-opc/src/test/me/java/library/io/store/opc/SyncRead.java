package me.java.library.io.store.opc;


import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.Server;

import java.util.Map;
import java.util.concurrent.Executors;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.opc.SyncRead
 * @createDate : 2020/7/24
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class SyncRead {

    private static int count;

    /**
     * Item的同步查询
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ConnectionInformation ci = new ConnectionInformation();
        ci.setHost("10.1.5.123");
        ci.setDomain("");
        ci.setUser("freud");
        ci.setPassword("password");
        ci.setClsid("F8582CF2-88FB-11D0-B850-00C0F0104305");

        Server server = new Server(ci,
                Executors.newSingleThreadScheduledExecutor());

        server.connect();

        Group group = server.addGroup();
        Item item = group.addItem("Random.Real5");

        Map<String, Item> items = group.addItems("Random.Real1",
                "Random.Real2", "Random.Real3", "Random.Real4");

        dumpItem(item);

        for (Map.Entry<String, Item> temp : items.entrySet()) {
            dumpItem(temp.getValue());
        }

        server.dispose();
    }

    private static void dumpItem(Item item) throws JIException {
        System.out.println("[" + (++count) + "],ItemName:[" + item.getId()
                + "],value:" + item.read(false).getValue());
    }


}
