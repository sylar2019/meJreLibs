package me.java.library.rpc.thrift.server.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ThriftServerConfigurationSelector.class)
public @interface EnableThriftServer {
}
