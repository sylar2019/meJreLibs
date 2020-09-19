package me.java.library.io.store.opc;


import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.Server;

import java.util.concurrent.Executors;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.opc.SyncWrite
 * @createDate : 2020/7/24
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class SyncWrite {

    private static int count;

    /**
     * 同步写入
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
        Item item = group.addItem("Square Waves.Real4");

        final Float[] integerData = new Float[]{1202f, 1203f, 1204f};
        final JIArray array = new JIArray(integerData, false);
        final JIVariant value = new JIVariant(array);

        item.write(value);
        Thread.sleep(2000);

        dumpItem(item);

        server.dispose();

    }

    private static void dumpItem(Item item) throws JIException {
        System.out.println("[" + (++count) + "],ItemName:[" + item.getId()
                + "],value:" + item.read(true).getValue());
    }
}
