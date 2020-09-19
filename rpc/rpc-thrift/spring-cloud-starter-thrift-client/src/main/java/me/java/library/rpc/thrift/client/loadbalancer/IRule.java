package me.java.library.rpc.thrift.client.loadbalancer;

import me.java.library.rpc.thrift.client.common.ThriftServerNode;

public interface IRule {

    ThriftServerNode choose(String key);

    ILoadBalancer getLoadBalancer();

    void setLoadBalancer(ILoadBalancer lb);

}
