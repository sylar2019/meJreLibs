package me.java.library.io.store.opc;


import org.openscada.opc.lib.common.ConnectionInformation;

import java.util.concurrent.Executors;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.opc.Pubsub
 * @createDate : 2020/7/24
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class Pubsub {
    private static final int PERIOD = 100;

    private static final int SLEEP = 2000;

    /**
     * Item的发布订阅查询
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

        AccessBase access = new Async20Access(server, PERIOD, false);

        access.addItem("Random.Real5", new DataCallback() {

            private int count;

            public void changed(Item item, ItemState itemstate) {
                System.out.println("[" + (++count) + "],ItemName:["
                        + item.getId() + "],value:" + itemstate.getValue());
            }
        });

        access.bind();
        Thread.sleep(SLEEP);
        access.unbind();
        server.dispose();
    }
}
