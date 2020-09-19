package me.java.library.rpc.thrift.client.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ThriftClientConfigurationSelector.class)
public @interface EnableThriftClient {
}
