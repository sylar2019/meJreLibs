package me.java.library.rpc.thrift.server.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.thrift.server.discovery.health-check")
public class ThriftServerHealthCheckProperties {

    /**
     * 是否允许健康检查
     */
    private Boolean enabled = true;

    /**
     * 服务健康检查时间间隔 (默认30s)
     */
    private String checkInterval = "10s";

    /**
     * 服务健康检查超时时间（默认3m）
     */
    private String checkTimeout = "30s";

    /**
     * 服务下线后注销服务（默认0s,不注销）
     */
    private String checkCriticalTimeout = "0s";


    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(String checkInterval) {
        this.checkInterval = checkInterval;
    }

    public String getCheckTimeout() {
        return checkTimeout;
    }

    public void setCheckTimeout(String checkTimeout) {
        this.checkTimeout = checkTimeout;
    }

    public String getCheckCriticalTimeout() {
        return checkCriticalTimeout;
    }

    public void setCheckCriticalTimeout(String checkCriticalTimeout) {
        this.checkCriticalTimeout = checkCriticalTimeout;
    }
}
