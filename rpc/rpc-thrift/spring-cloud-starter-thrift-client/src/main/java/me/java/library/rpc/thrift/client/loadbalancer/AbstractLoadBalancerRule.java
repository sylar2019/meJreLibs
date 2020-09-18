package me.java.library.rpc.thrift.client.loadbalancer;

public abstract class AbstractLoadBalancerRule implements IRule {

    protected ILoadBalancer lb;

    @Override
    public ILoadBalancer getLoadBalancer() {
        return lb;
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.lb = lb;
    }
}
