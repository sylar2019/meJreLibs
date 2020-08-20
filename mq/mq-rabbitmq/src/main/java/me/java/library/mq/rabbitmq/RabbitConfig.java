package me.java.library.mq.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public static final String IOT_EXCHANGE = "iot_exchange";
    public static final String IOT_QUEUE = "iot_queue";

    @Bean(IOT_EXCHANGE)
    public Exchange iotExchange() {
        return ExchangeBuilder.topicExchange(IOT_EXCHANGE).durable(true).build();
    }

    @Bean(IOT_QUEUE)
    public Queue iotQueue() {
        return QueueBuilder.durable(IOT_QUEUE).build();
    }

    @Bean
    public Binding binding(@Qualifier(IOT_QUEUE) Queue queue,
                           @Qualifier(IOT_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("iot.#").noargs();
    }
}
