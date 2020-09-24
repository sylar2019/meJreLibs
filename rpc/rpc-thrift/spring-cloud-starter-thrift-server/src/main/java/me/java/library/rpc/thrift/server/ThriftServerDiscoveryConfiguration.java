package me.java.library.rpc.thrift.server;

import com.ecwid.consul.v1.ConsulClient;
import me.java.library.rpc.thrift.server.properties.ThriftServerDiscoveryProperties;
import me.java.library.rpc.thrift.server.properties.ThriftServerProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.net.UnknownHostException;

@Configuration
@AutoConfigureAfter(ThriftServerAutoConfiguration.class)
@ConditionalOnProperty(name = "spring.thrift.server.discovery.enabled", havingValue = "true")
@Import(ThriftServerAutoConfiguration.class)
public class ThriftServerDiscoveryConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ConsulClient thriftConsulClient(ThriftServerProperties thriftServerProperties) throws UnknownHostException {
        ThriftServerDiscoveryProperties discoveryProperties = thriftServerProperties.getDiscovery();
        return new ConsulClient(discoveryProperties.getHost(), discoveryProperties.getPort());
    }

    @Bean
    @ConditionalOnMissingBean
    public ThriftServerRegister thriftServerRegister(ConsulClient consulClient, ThriftServerProperties thriftServerProperties) throws UnknownHostException {
        return new ThriftServerRegister(consulClient, thriftServerProperties);
    }

}
