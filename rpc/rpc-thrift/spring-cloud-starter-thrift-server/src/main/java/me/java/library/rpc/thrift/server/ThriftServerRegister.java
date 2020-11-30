package me.java.library.rpc.thrift.server;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import lombok.extern.slf4j.Slf4j;
import me.java.library.rpc.thrift.server.properties.ThriftServerDiscoveryProperties;
import me.java.library.rpc.thrift.server.properties.ThriftServerHealthCheckProperties;
import me.java.library.rpc.thrift.server.properties.ThriftServerProperties;
import org.apache.commons.collections4.CollectionUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

@Slf4j
public class ThriftServerRegister {

    private static final String REGISTRY_URL_TEMPLATE = "http://%s:%d";
    private static final String HEALTH_CHECK_URL_TEMPLATE = "%s:%d";

    public ThriftServerRegister(ConsulClient consulClient, ThriftServerProperties thriftServerProperties) throws UnknownHostException {
        ThriftServerDiscoveryProperties discoveryProperties = thriftServerProperties.getDiscovery();
        String discoveryHostAddress = discoveryProperties.getHost();
        Integer discoveryPort = discoveryProperties.getPort();
        log.info("Service discovery server host {}, port {}", discoveryHostAddress, discoveryPort);

        String serviceName = thriftServerProperties.getServiceId();
        String serverHostAddress = Inet4Address.getLocalHost().getHostAddress();
        int servicePort = thriftServerProperties.getPort();
        String serviceId = String.join(":", serviceName, serverHostAddress, String.valueOf(servicePort));

        log.info("Service id {}", serviceId);
        log.info("Service name {}", serviceName);
        log.info("Service host address {}, port {}", serverHostAddress, servicePort);

        List<String> serviceTags = discoveryProperties.getTags();
        if (CollectionUtils.isNotEmpty(serviceTags)) {
            log.info("Service tags [{}]", String.join(", ", serviceTags));
        }

        registerAgentService(discoveryProperties,
                serviceName,
                serverHostAddress,
                servicePort,
                serviceId,
                serviceTags,
                consulClient);
    }


    private void registerAgentService(ThriftServerDiscoveryProperties discoveryProperties,
                                      String serviceName,
                                      String serverHostAddress,
                                      int servicePort,
                                      String serviceId,
                                      List<String> serviceTags,
                                      ConsulClient consulClient) {
        NewService newService = new NewService();
        newService.setId(serviceId);
        newService.setName(serviceName);
        newService.setTags(serviceTags);
        newService.setAddress(serverHostAddress);
        newService.setPort(servicePort);

        ThriftServerHealthCheckProperties healthCheckProperties = discoveryProperties.getHealthCheck();
        if (healthCheckProperties.getEnabled()) {
            String healthCheckUrl = String.format(HEALTH_CHECK_URL_TEMPLATE, serverHostAddress, servicePort);
            String checkInterval = healthCheckProperties.getCheckInterval();
            String checkTimeout = healthCheckProperties.getCheckTimeout();
            String checkCriticalTimeout = healthCheckProperties.getCheckCriticalTimeout();

            log.info("Service health check tcp url: {}", healthCheckUrl);
            log.info("Service health check interval: {}, timeout: {}, check critical timeout: {}",
                    checkInterval,
                    checkTimeout,
                    checkCriticalTimeout);

            NewService.Check serviceCheck = new NewService.Check();
            serviceCheck.setTcp(healthCheckUrl);
            serviceCheck.setInterval(checkInterval);
            serviceCheck.setTimeout(checkTimeout);
            serviceCheck.setDeregisterCriticalServiceAfter(checkCriticalTimeout);
            newService.setCheck(serviceCheck);
        }

        consulClient.agentServiceRegister(newService);
    }
}
