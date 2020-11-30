package me.java.library.mq.kafka.loop;

import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import lombok.extern.slf4j.Slf4j;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import me.java.library.mq.kafka.KafkaConst;
import me.java.library.utils.base.ConcurrentUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Header;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * @author :  sylar
 * @FileName :
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
@Slf4j
public class PullLoop implements Runnable {

    KafkaConsumer<String, String> consumer;
    MessageListener listener;
    String[] tags;


    AtomicBoolean shutdown;
    CountDownLatch shutdownLatch;
    ExecutorService executorService;

    public PullLoop(KafkaConsumer<String, String> consumer, MessageListener listener, String... tags) {
        assert consumer != null;
        assert listener != null;
        this.consumer = consumer;
        this.listener = listener;
        this.tags = tags;
    }

    public void start() {
        this.shutdown = new AtomicBoolean(false);
        this.shutdownLatch = new CountDownLatch(1);

        executorService = ConcurrentUtils.simpleThreadPool();
        executorService.execute(this);
    }

    public void stop() {
        try {
            shutdown.set(true);
            shutdownLatch.await();
            executorService.shutdown();
        } catch (Exception e) {
            onError(e);
        } finally {
            shutdown = null;
            shutdownLatch = null;
            executorService = null;
        }
    }

    @Override
    public void run() {
        try {
            while (!shutdown.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));
                //处理收到的消息
                records.forEach(record -> {
                    System.out.println("### ConsumerRecord: " + record);
                    onRecord(record);
                });

                //异步确认
                consumer.commitAsync();
            }
        } catch (Exception e) {
            onError(e);
        } finally {
            try {
                //同步确认
                consumer.commitSync();
            } catch (Exception e) {
                log.error("【kafka】消息同步确认异常。", e);
            } finally {
                consumer.close();
                shutdownLatch.countDown();
            }
        }
    }

    void onError(Throwable t) {
        listener.onFailure(t);
    }


    void onRecord(ConsumerRecord<String, String> record) {
        String tag = getTagValue(record);
        boolean isMatched = tags == null || Stream.of(tags).anyMatch((Predicate<String>) input -> Objects.equals(input, tag));

        if (isMatched) {
            Message message = new Message(record.topic(), record.value());
            message.setKey(record.key());
            message.setTag(tag);
            listener.onSuccess(message);
        } else {
            //收到的消息，与订阅的tag不匹配
            log.warn(String.format("【kakfa】订阅的tag不匹配。 订阅的tags: %s , 收到的tag: %s", Arrays.toString(tags), tag));
        }
    }

    String getTagValue(ConsumerRecord<String, String> record) {
        Header header = record.headers().lastHeader(KafkaConst.MESSAGE_TAG);
        if (header != null && header.value() != null) {
            return new String(header.value(), Charsets.UTF_8);
        }
        return null;
    }


}