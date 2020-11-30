package me.java.library.rpc.thrift.client.annotation;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import me.java.library.rpc.thrift.client.properties.TServiceModel;
import org.springframework.cloud.commons.util.SpringFactoryImportSelector;
import org.springframework.core.env.Environment;

import java.util.Set;

@Slf4j
public class ThriftClientConfigurationSelector extends SpringFactoryImportSelector<EnableThriftClient> {


    private static final Set<String> SERVICE_MODEL_SET = Sets.newHashSet("simple",
            "nonBlocking", "threadPool", "hsHa", "threadedSelector");
    private static final String SERVICE_MODEL = "spring.thrift.client.service-model";

    @Override
    protected boolean isEnabled() {
        Environment environment = getEnvironment();
        String serviceModel = environment.getProperty(SERVICE_MODEL, String.class, TServiceModel.SERVICE_MODEL_DEFAULT);
        boolean enableAutoConfiguration = SERVICE_MODEL_SET.contains(serviceModel);
        if (enableAutoConfiguration) {
            log.info("Enable thrift client auto configuration, service model {}", serviceModel);
        }
        return enableAutoConfiguration;
    }
}
