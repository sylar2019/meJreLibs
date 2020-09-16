package me.java.library.rpc.thrift.client.discovery;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.health.HealthServicesRequest;
import com.ecwid.consul.v1.health.model.HealthService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.java.library.rpc.thrift.client.common.ThriftServerNodeList;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class ThriftConsulServerNodeList extends ThriftServerNodeList<ThriftConsulServerNode> {

    private final ConsulClient consulClient;

    private static ThriftConsulServerNodeList serverNodeList = null;

    public static ThriftConsulServerNodeList singleton(ConsulClient consulClient) {
        if (serverNodeList == null) {
            synchronized (ThriftConsulServerNodeList.class) {
                if (serverNodeList == null) {
                    serverNodeList = new ThriftConsulServerNodeList(consulClient);
                }
            }
        }
        return serverNodeList;
    }

    private ThriftConsulServerNodeList(ConsulClient consulClient) {
        this.consulClient = consulClient;
    }

    @Override
    public List<ThriftConsulServerNode> getThriftServer(String serviceName) {
        if (MapUtils.isNotEmpty(this.serverNodeMap) && (this.serverNodeMap.containsKey(serviceName))) {
            LinkedHashSet<ThriftConsulServerNode> serverNodeSet = this.serverNodeMap.get(serviceName);
            if (CollectionUtils.isNotEmpty(serverNodeSet)) {
                return Lists.newArrayList(serverNodeSet);
            }
        }

        return refreshThriftServer(serviceName);
    }

    @Override
    public List<ThriftConsulServerNode> refreshThriftServer(String serviceName) {
        List<ThriftConsulServerNode> serverNodeList = Lists.newArrayList();
        List<HealthService> healthServices = getHealthServices(serviceName);
        filterAndCompoServerNodes(serverNodeList, healthServices);

        if (CollectionUtils.isNotEmpty(serverNodeList)) {
            this.serverNodeMap.put(serviceName, Sets.newLinkedHashSet(serverNodeList));
        }

        return serverNodeList;
    }


    @Override
    public Map<String, LinkedHashSet<ThriftConsulServerNode>> getThriftServers() {
        if (MapUtils.isNotEmpty(this.serverNodeMap)) {
            return this.serverNodeMap;
        }

        return refreshThriftServers();
    }

    @Override
    public Map<String, LinkedHashSet<ThriftConsulServerNode>> refreshThriftServers() {
        Map<String, List<String>> catalogServiceMap = consulClient.getCatalogServices(QueryParams.DEFAULT).getValue();
        if (MapUtils.isEmpty(catalogServiceMap)) {
            return this.serverNodeMap;
        }

        Map<String, LinkedHashSet<ThriftConsulServerNode>> serverNodeMap = Maps.newConcurrentMap();
        for (Map.Entry<String, List<String>> catalogServiceEntry : catalogServiceMap.entrySet()) {
            String serviceName = catalogServiceEntry.getKey();
            List<String> tags = catalogServiceEntry.getValue();

//            if (CollectionUtils.isEmpty(tags)) {
//                continue;
//            }

            List<HealthService> healthServices = getHealthServices(serviceName);
            List<ThriftConsulServerNode> serverNodes = Lists.newArrayList();
            filterAndCompoServerNodes(serverNodes, healthServices);
            LinkedHashSet<ThriftConsulServerNode> serverNodeSet = Sets.newLinkedHashSet(serverNodes);

            if (CollectionUtils.isNotEmpty(serverNodeSet)) {
                serverNodeMap.put(serviceName, serverNodeSet);
            }
        }

        this.serverNodeMap.clear();
        this.serverNodeMap.putAll(serverNodeMap);
        return ImmutableMap.copyOf(this.serverNodeMap);
    }


    private static ThriftConsulServerNode getThriftConsulServerNode(HealthService healthService) {
        ThriftConsulServerNode serverNode = new ThriftConsulServerNode();

        HealthService.Node node = healthService.getNode();
        serverNode.setNode(node.getNode());

        HealthService.Service service = healthService.getService();
        serverNode.setAddress(service.getAddress());
        serverNode.setPort(service.getPort());
        serverNode.setHost(ThriftConsulServerUtils.findHost(healthService));

        serverNode.setServiceId(service.getService());
        serverNode.setTags(service.getTags());
        serverNode.setHealth(ThriftConsulServerUtils.isPassingCheck(healthService));

        return serverNode;
    }


    private void filterAndCompoServerNodes(List<ThriftConsulServerNode> serverNodeList, List<HealthService> healthServices) {

        for (HealthService healthService : healthServices) {
            ThriftConsulServerNode serverNode = getThriftConsulServerNode(healthService);

            if (!serverNode.isHealth()) {
                continue;
            }

//            if (CollectionUtils.isEmpty(serverNode.getTags())) {
//                continue;
//            }

            serverNodeList.add(serverNode);
        }
    }


    private List<HealthService> getHealthServices(String serviceName) {
        HealthServicesRequest request = HealthServicesRequest.newBuilder()
                .setPassing(true)
                .setQueryParams(QueryParams.DEFAULT)
                .build();

        return consulClient.getHealthServices(serviceName, request).getValue();
    }
}
