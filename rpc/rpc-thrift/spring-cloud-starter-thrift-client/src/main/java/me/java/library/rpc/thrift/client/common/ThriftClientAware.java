package me.java.library.rpc.thrift.client.common;

import org.apache.thrift.TServiceClient;

public interface ThriftClientAware<T extends TServiceClient> {

    T client();
}
