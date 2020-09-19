package me.java.library.rpc.thrift.client.common;

import me.java.library.rpc.thrift.client.pool.TransportKeyedObjectPool;
import me.java.library.rpc.thrift.client.properties.ThriftClientProperties;

import java.net.URI;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThriftClientContext {

    private static final Lock lock = new ReentrantLock();

    private static ThriftClientContext context;
    private ThriftClientProperties properties;
    private TransportKeyedObjectPool objectPool;
    private URI registryUri;

    private ThriftClientContext() {
    }

    public static ThriftClientContext context(ThriftClientProperties properties, TransportKeyedObjectPool objectPool) {
        context().properties = properties;
        context().objectPool = objectPool;
        return context;
    }

    public static ThriftClientContext context() {
        if (context == null) {
            try {
                lock.lock();
                if (context == null) {
                    context = new ThriftClientContext();
                }
            } finally {
                lock.unlock();
            }
        }
        return context;
    }

    public static void registry(URI registryUri) {
        context().registryUri = registryUri;
    }

    public ThriftClientProperties getProperties() {
        return context.properties;
    }

    public TransportKeyedObjectPool getObjectPool() {
        return context.objectPool;
    }

    public URI getRegistryUri() {
        return context.registryUri;
    }
}
