package me.java.library.rpc.thrift.client.exception;

public class ThriftClientRequestTimeoutException extends RuntimeException {

    public ThriftClientRequestTimeoutException(String message) {
        super(message);
    }

    public ThriftClientRequestTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
