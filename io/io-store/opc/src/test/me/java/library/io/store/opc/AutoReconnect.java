package me.java.library.io.store.opc;


import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.*;

import java.util.concurrent.Executors;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.opc.AutoReconnect
 * @createDate : 2020/7/24
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class AutoReconnect {

    private static final int PERIOD = 100;

    private static final int SLEEP = 2000;

    /**
     * 自动重连Item异步读取
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

        AutoReconnectController controller = new AutoReconnectController(server);

        controller.connect();

        AccessBase access = new SyncAccess(server, PERIOD);

        access.addItem("Random.Real5", new DataCallback() {
            private int i;

            public void changed(Item item, ItemState itemstate) {
                System.out.println("[" + (++i) + "],ItemName:[" + item.getId()
                        + "],value:" + itemstate.getValue());
            }
        });

        access.bind();
        Thread.sleep(SLEEP);
        access.unbind();
        controller.disconnect();
    }
}
