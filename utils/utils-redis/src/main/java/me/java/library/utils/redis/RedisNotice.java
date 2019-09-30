package me.java.library.utils.redis;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author :  sylar
 * @FileName :  RedisNotice
 * @CreateDate :  2017/11/08
 * @Description : 基于redis实现的消息订阅发布
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class RedisNotice {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisMessageListenerContainer redisMessageListenerContainer;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(JedisConnectionFactory jedisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);

        Executor subscriptionExecutor = new ThreadPoolExecutor(
                10,
                Integer.MAX_VALUE,
                10L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("subscription-pool-%d").build());

        Executor taskExecutor = new ThreadPoolExecutor(
                10,
                Integer.MAX_VALUE,
                10L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("taskDetail-pool-%d").build());

        container.setSubscriptionExecutor(subscriptionExecutor);
        container.setTaskExecutor(taskExecutor);
        return container;
    }

    /**
     * 发布消息
     *
     * @param topic   消息主题
     * @param message 消息内容
     */
    public void publish(String topic, String message) {
        Preconditions.checkNotNull(topic, "topic is null");
        Preconditions.checkNotNull(message, "msg is null");

        stringRedisTemplate.convertAndSend(topic, message);

    }

    /**
     * 订阅消息
     *
     * @param messageListener 消息监听器
     * @param topics          消息主题列表
     */
    public void subscribe(MessageListener messageListener, List<String> topics) {
        Preconditions.checkNotNull(messageListener, "messageListener is null");
        Preconditions.checkNotNull(topics, "topics is null");
        Preconditions.checkState(topics.size() > 0, "invalid topics");

        //先取消订阅
        unsubscribe(messageListener, topics);

        //重新订阅
        List<Topic> channelTopics = convertPatternTopics(topics);
        redisMessageListenerContainer.addMessageListener(messageListener, channelTopics);
    }

    /**
     * 取消订阅
     *
     * @param messageListener 消息监听器
     * @param topics          消息主题列表
     */
    public void unsubscribe(MessageListener messageListener, List<String> topics) {
        Preconditions.checkNotNull(messageListener, "messageListener is null");

        List<Topic> channelTopics = convertPatternTopics(topics);
        redisMessageListenerContainer.removeMessageListener(messageListener, channelTopics);
    }

    private List<Topic> convertPatternTopics(List<String> topics) {
        List<Topic> res = Lists.newArrayList();
        if (topics != null) {
            topics.forEach(topic -> {
                if (!Strings.isNullOrEmpty(topic)) {
                    res.add(new PatternTopic(topic));
                }
            });
        }

        return res;
    }
}
