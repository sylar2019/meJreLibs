package me.java.library.rpc.thrift.server.argument;

import me.java.library.rpc.thrift.server.exception.ThriftServerException;
import me.java.library.rpc.thrift.server.processor.TRegisterProcessor;
import me.java.library.rpc.thrift.server.processor.TRegisterProcessorFactory;
import me.java.library.rpc.thrift.server.properties.ThriftServerProperties;
import me.java.library.rpc.thrift.server.wrapper.ThriftServiceWrapper;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TNonBlockingServerArgument extends TNonblockingServer.Args {

    private Map<String, ThriftServiceWrapper> processorMap = new HashMap<>();

    public TNonBlockingServerArgument(List<ThriftServiceWrapper> serviceWrappers, ThriftServerProperties properties)
            throws TTransportException {
        super(new TNonblockingServerSocket(properties.getPort()));

        transportFactory(new TFastFramedTransport.Factory());
        protocolFactory(new TCompactProtocol.Factory());

        try {
            TRegisterProcessor registerProcessor = TRegisterProcessorFactory.registerProcessor(serviceWrappers);

            processorMap.clear();
            processorMap.putAll(registerProcessor.getProcessorMap());

            processor(registerProcessor);
        } catch (Exception e) {
            throw new ThriftServerException("Can not create multiplexed processor for " + serviceWrappers, e);
        }
    }

    public Map<String, ThriftServiceWrapper> getProcessorMap() {
        return processorMap;
    }
}
