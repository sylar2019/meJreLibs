package me.java.library.io.store.opc.client;

import com.google.common.base.Preconditions;
import me.java.library.io.base.cmd.Cmd;
import me.java.library.io.base.pipe.BasePipe;
import me.java.library.io.store.opc.OpcParam;
import me.java.library.io.store.opc.cmd.*;
import me.java.library.utils.base.ConcurrentUtils;
import me.java.library.utils.base.ExceptionUtils;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.da.*;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * File Name             :  OpcClientPipe
 *
 * @author :  sylar
 * Create                :  2020/7/23
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
public class OpcClientPipe extends BasePipe {

    Server server;
    AutoReconnectController controller;
    AccessBase subSccess;

    public OpcClientPipe(OpcParam param) {
        server = new Server(param.convert(), ConcurrentUtils.simpleScheduledThreadPool());
        controller = new AutoReconnectController(server);
    }

    @Override
    protected boolean onStart() throws Exception {
        if (isDaemon) {
            controller.connect();
        } else {
            server.connect();
        }
        return true;
    }

    @Override
    protected boolean onStop() throws Exception {
        if (isDaemon) {
            controller.disconnect();
        } else {
            server.disconnect();
        }
        return true;
    }

    @Override
    protected boolean onSend(Cmd request) throws Exception {
        Group group = server.addGroup();
        if (request instanceof OpcReadRequestCmd) {
            OpcReadRequestCmd readRequestCmd = (OpcReadRequestCmd) request;
            Map<String, Item> items = group.addItems(readRequestCmd.getItems().toArray(new String[]{}));

            CountDownLatch countDownLatch = new CountDownLatch(items.size());
            OpcReadResponseCmd readResponseCmd = new OpcReadResponseCmd();

            AccessBase access = new SyncAccess(server, 1000);

            items.values().forEach(item -> {
                try {
                    access.addItem(item.getId(), new DataCallback() {
                        @Override
                        public void changed(Item item, ItemState itemState) {
                            readResponseCmd.getResult().put(item.getId(), itemState.getValue());
                            countDownLatch.countDown();
                        }
                    });
                } catch (JIException | AddFailedException e) {
                    e.printStackTrace();
                    countDownLatch.countDown();
                }
            });

            access.bind();

            countDownLatch.await();
            access.unbind();

            onReceived(readResponseCmd);

        }else if (request instanceof OpcWriteRequestCmd) {
            ExceptionUtils.throwException("OPC官写操不支持异步方式");
        }
        else if (request instanceof OpcStartSubscribeCmd) {
            startSub((OpcStartSubscribeCmd) request);
        } else if (request instanceof OpcStopSubscribeCmd) {
            stopSub((OpcStopSubscribeCmd) request);
        } else {
            ExceptionUtils.notSupportMethod();
        }

        return true;
    }

    @Override
    protected Cmd onSyncSend(Cmd request, long timeout, TimeUnit unit) throws Exception {
        Group group = server.addGroup();

        if (request instanceof OpcReadRequestCmd) {
            OpcReadRequestCmd readRequestCmd = (OpcReadRequestCmd) request;
            Map<String, Item> items = group.addItems(readRequestCmd.getItems().toArray(new String[]{}));

            OpcReadResponseCmd readResponseCmd = new OpcReadResponseCmd();
            items.values().forEach(item -> {
                try {
                    readResponseCmd.getResult().put(item.getId(), item.read(false).getValue());
                } catch (JIException e) {
                    e.printStackTrace();
                }
            });

            return readResponseCmd;

        } else if (request instanceof OpcWriteRequestCmd) {
            OpcWriteRequestCmd writeRequestCmd = (OpcWriteRequestCmd) request;

            Map<String, JIVariant> values = writeRequestCmd.getValues();
            Map<String, Item> items = group.addItems(values.keySet().toArray(new String[]{}));

            OpcWriteResponseCmd writeResponseCmd = new OpcWriteResponseCmd();
            items.values().forEach(item -> {
                try {
                    item.write(values.get(item.getId()));
                } catch (JIException e) {
                    writeResponseCmd.getResult().put(item.getId(), e);
                    writeResponseCmd.setErrorCount(writeResponseCmd.getErrorCount() + 1);
                }
            });
            return writeResponseCmd;
        }else{
            ExceptionUtils.notSupportMethod();
        }
        return null;
    }

    @Override
    protected void checkOnSend(Cmd request) {
        super.checkOnSend(request);
        Preconditions.checkState(request instanceof OpcCmd);
    }

    private void startSub(OpcStartSubscribeCmd startCmd) throws Exception {
        if (subSccess != null) {
            throw new IllegalAccessException("重复启动订阅");
        }

        subSccess = new Async20Access(server, startCmd.getPeriod(), false);
        startCmd.getItems().forEach(item -> {
            try {
                subSccess.addItem(item, startCmd.getDataCallback());
            } catch (JIException | AddFailedException e) {
                e.printStackTrace();
            }
        });

        subSccess.bind();
    }

    private void stopSub(OpcStopSubscribeCmd stopCmd) throws Exception {
        if (subSccess == null) {
            throw new IllegalAccessException("未启动订阅");
        }

        subSccess.unbind();
    }
}
