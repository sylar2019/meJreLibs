package me.java.library.mq.kafka.loop;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import me.java.library.mq.base.Message;
import me.java.library.mq.base.MessageListener;
import me.java.library.utils.base.ConcurrentUtils;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

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
public class PullLoop implements Runnable {
    protected KafkaConsumer<String, String> consumer;
    protected MessageListener listener;

    private AtomicBoolean shutdown;
    private CountDownLatch shutdownLatch;
    private ExecutorService executorService;

    public PullLoop(KafkaConsumer<String, String> consumer, MessageListener listener) {
        this.consumer = consumer;
        this.listener = listener;
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
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                if (onProcessMessagesAndConfirm(records)) {
//                    consumer.commitAsync();     //异步确认
                    doCommitSync();             //同步确认
                }
            }
        } catch (WakeupException e) {
            // ignore, we're closing
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            onError(e);
        } finally {
            consumer.close();
            shutdownLatch.countDown();
        }
    }

    protected boolean onProcessMessagesAndConfirm(ConsumerRecords<String, String> records) {
        if (listener != null) {
            records.forEach(record -> {
                Message message = new Message(record.topic(), record.value());
                message.setExt(record);
                message.setKeys(record.key());
                listener.onSuccess(message);
            });
            return true;
        } else {
            return false;
        }
    }


    private void doCommitSync() {
        try {
            consumer.commitSync();
        } catch (WakeupException e) {
            // we're shutting down, but finish the commit first and then
            // rethrow the exception so that the main loop can exit
            doCommitSync();
            throw e;
        } catch (CommitFailedException e) {
            // the commit failed with an unrecoverable error. if there is any
            // internal state which depended on the commit, you can clean it
            // up here. otherwise it's reasonable to ignore the error and go on
//            log.debug("Commit failed", e);
        }
    }

    void onError(Throwable t) {
        if (listener != null) {
            listener.onFailure(t);
        }
    }
}