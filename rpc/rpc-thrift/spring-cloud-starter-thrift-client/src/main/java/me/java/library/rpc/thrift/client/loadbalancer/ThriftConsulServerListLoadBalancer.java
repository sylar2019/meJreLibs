package me.java.library.rpc.thrift.client.loadbalancer;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.java.library.rpc.thrift.client.common.ThriftServerNode;
import me.java.library.rpc.thrift.client.discovery.ServerListUpdater;
import me.java.library.rpc.thrift.client.discovery.ThriftConsulServerListUpdater;
import me.java.library.rpc.thrift.client.discovery.ThriftConsulServerNode;
import me.java.library.rpc.thrift.client.discovery.ThriftConsulServerNodeList;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Slf4j
public class ThriftConsulServerListLoadBalancer extends AbstractLoadBalancer {

    private ThriftConsulServerNodeList serverNodeList;
    private final ServerListUpdater.UpdateAction updateAction = this::updateListOfServers;
    private IRule rule;
    private volatile ServerListUpdater serverListUpdater;

    public ThriftConsulServerListLoadBalancer(ThriftConsulServerNodeList serverNodeList, IRule rule) {
        this.serverNodeList = serverNodeList;
        this.rule = rule;
        this.serverListUpdater = new ThriftConsulServerListUpdater();
        this.startUpdateAction();
    }

    @Override
    public ThriftConsulServerNodeList getThriftServerNodeList() {
        return this.serverNodeList;
    }

    @Override
    public ThriftConsulServerNode chooseServerNode(String key) {
        if (rule == null) {
            return null;
        } else {
            ThriftServerNode serverNode;
            try {
                serverNode = rule.choose(key);
            } catch (Exception e) {
                log.warn("LoadBalancer [{}]:  Error choosing server for key {}", getClass().getSimpleName(), key, e);
                return null;
            }

            if (serverNode instanceof ThriftConsulServerNode) {
                return (ThriftConsulServerNode) serverNode;
            }
        }

        return null;
    }

    private synchronized void startUpdateAction() {
        log.info("Using serverListUpdater {}", serverListUpdater.getClass().getSimpleName());
        if (serverListUpdater == null) {
            serverListUpdater = new ThriftConsulServerListUpdater();
        }

        this.serverListUpdater.start(updateAction);
    }

    public void stopServerListRefreshing() {
        if (serverListUpdater != null) {
            serverListUpdater.stop();
        }
    }

    private void updateListOfServers() {
        Map<String, LinkedHashSet<ThriftConsulServerNode>> thriftConsulServers = this.serverNodeList.refreshThriftServers();

        List<String> serverList = Lists.newArrayList();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, LinkedHashSet<ThriftConsulServerNode>> serverEntry : thriftConsulServers.entrySet()) {
            serverList.add(
                    sb.append(serverEntry.getKey())
                            .append(": ")
                            .append(serverEntry.getValue())
                            .toString()
            );
            sb.setLength(0);
        }

        sb = new StringBuilder("\nRefreshed thrift serverList,count: 【");
        sb.append(serverList.size()).append("】\n");
        for (int i = 0; i < serverList.size(); i++) {
            sb.append(String.format("###【%s】###\t %s \n", i + 1, serverList.get(i)));
        }
        log.info(sb.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ThriftConsulServerListLoadBalancer:");
        sb.append(super.toString());
        sb.append("ServerList:").append(String.valueOf(serverNodeList));
        return sb.toString();
    }
}
