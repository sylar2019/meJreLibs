package me.util.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

/**
 * @author :  sylar
 * @FileName :  DisruptorHub
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
public class DisruptorHub {

    final static private int RING_BUFFER_SIZE = 1024 * 16;
    private Disruptor<ValueEvent> disruptor;
    private RingBuffer<ValueEvent> ringBuffer;

    @SuppressWarnings("unchecked")
    public DisruptorHub(EventHandler<ValueEvent>... eventHandlers) {
        disruptor = new Disruptor<>(new ValueEventFactory(), RING_BUFFER_SIZE, Executors.defaultThreadFactory());
        disruptor.setDefaultExceptionHandler(new ValueEventExceptionHandler());
        disruptor.handleEventsWith(eventHandlers);
        disruptor.start();
        ringBuffer = disruptor.getRingBuffer();
    }

    public void stop() {
        if (disruptor != null) {
            disruptor.shutdown();
        }
    }

    public void publish(Object value) {
        if (ringBuffer != null) {
            long sequence = ringBuffer.next();
            try {
                ValueEvent event = ringBuffer.get(sequence);
                event.setValue(value);
            } finally {
                ringBuffer.publish(sequence);
            }
        }
    }

}
