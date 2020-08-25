package me.java.library.mq.starter;

import me.java.library.mq.base.Factory;
import me.java.library.mq.base.MqProperties;
import me.java.library.mq.kafka.KafkaFactory;
import me.java.library.mq.local.LocalFactory;
import me.java.library.mq.ons.http.OnsHttpFactory;
import me.java.library.mq.ons.mqtt.OnsMqttFactory;
import me.java.library.mq.ons.tcp.OnsTcpFactory;
import me.java.library.mq.rabbitmq.RabbitFactory;
import me.java.library.mq.redis.RedisFactory;
import me.java.library.mq.rocketmq.RocketmqFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * File Name             :  MqAutoConfiguration
 * Author                :  sylar
 * Create Date           :  2018/4/18
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
@Configuration
@EnableConfigurationProperties(DefaultMqProperties.class)
public class MqConfig {

    @Autowired
    private DefaultMqProperties mqProperties;

    @Autowired
    ApplicationContext ctx;

    @Bean
    @ConditionalOnMissingBean(Factory.class)
    public Factory getFactory() {
        switch (mqProperties.getProvider().toLowerCase()) {
            case MqProperties.PROVIDER_KAFKA:
                return new KafkaFactory(mqProperties);
            case MqProperties.PROVIDER_ROCKETMQ:
                return new RocketmqFactory(mqProperties);
            case MqProperties.PROVIDER_RABBITMQ:
                injectRabbitProperties();
                return new RabbitFactory(mqProperties);
            case MqProperties.PROVIDER_ONS_TCP:
                return new OnsTcpFactory(mqProperties);
            case MqProperties.PROVIDER_ONS_HTTP:
                return new OnsHttpFactory(mqProperties);
            case MqProperties.PROVIDER_ONS_MQTT:
                return new OnsMqttFactory(mqProperties);
            case MqProperties.PROVIDER_REDIS:
                injectRedisProperties();
                return new RedisFactory(mqProperties);
            case MqProperties.PROVIDER_LOCAL:
                mqProperties.setBrokers("LOCAL");
                return new LocalFactory(mqProperties);
            default:
                return null;
        }
    }

    void injectRedisProperties() {
        RedisProperties redisProperties = ctx.getBean(RedisProperties.class);
        if (redisProperties.getUrl() == null) {
            redisProperties.setUrl(String.format("redis://%s", mqProperties.getBrokers()));
        }
        if (redisProperties.getPassword() == null) {
            redisProperties.setPassword(mqProperties.getPassword());
        }
    }

    void injectRabbitProperties() {
        RabbitProperties rabbitProperties = ctx.getBean(RabbitProperties.class);
        URI uri = URI.create("rabbit://" + mqProperties.getBrokers());
        rabbitProperties.setHost(uri.getHost());
        rabbitProperties.setPort(uri.getPort());
        rabbitProperties.setUsername(mqProperties.getUser());
        rabbitProperties.setPassword(mqProperties.getPassword());

        String virtualHost = mqProperties.getAttrOrDefault("virtualHost", "/");
        rabbitProperties.setVirtualHost(virtualHost);
    }
}
