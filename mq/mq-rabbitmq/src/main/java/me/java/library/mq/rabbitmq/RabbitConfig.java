package me.java.library.mq.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : sylar
 * @fullName : me.java.library.mq.rabbitmq.RabbitConfig
 * @createDate : 2020/8/20
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
@Configuration
public class RabbitConfig {
    public static final String EXCHANGE_NAME = "me.mq";

    @Autowired
    RabbitProperties properties;

    @Bean
    public ConnectionFactory factory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(properties.getHost());
        factory.setPort(properties.getPort());
        factory.setVirtualHost(properties.getVirtualHost());
        factory.setUsername(properties.getUsername());
        factory.setPassword(properties.getPassword());
        return factory;
    }

}
