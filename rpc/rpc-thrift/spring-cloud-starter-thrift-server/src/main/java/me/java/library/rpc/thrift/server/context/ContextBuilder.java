package me.java.library.rpc.thrift.server.context;

import me.java.library.rpc.thrift.server.properties.ThriftServerProperties;
import me.java.library.rpc.thrift.server.wrapper.ThriftServiceWrapper;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.util.List;

public interface ContextBuilder {

    ContextBuilder prepare();

    TServer buildThriftServer(ThriftServerProperties properties,
                              List<ThriftServiceWrapper> serviceWrappers)
            throws TTransportException, IOException;

}
