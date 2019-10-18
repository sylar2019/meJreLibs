package me.java.library.utils.event.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import me.java.library.utils.event.IEvent;

import java.util.concurrent.Executors;

/**
 * File Name             :  EventService
 *
 * @Author :  sylar
 * @Create :  2019-10-07
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class EventService {

    final static private int RING_BUFFER_SIZE = 1024 * 16;
    private Disruptor<Event> disruptor;

    public EventService(EventHandler<Event> handler) {
        disruptor = new Disruptor<>(new EventFactory(),
                RING_BUFFER_SIZE,
                Executors.defaultThreadFactory());

        disruptor.setDefaultExceptionHandler(new EventExceptionHandler());
        disruptor.handleEventsWith(handler);
        disruptor.start();
    }

    public void dispose() {
        if (disruptor != null) {
            disruptor.shutdown();
            disruptor = null;
        }
    }

    public void publish(IEvent<?, ?> iEvent) {
        if (disruptor != null) {
            RingBuffer<Event> ringBuffer = disruptor.getRingBuffer();
            ringBuffer.publishEvent((event, sequence, data) -> event.setValue(data), iEvent);
        }
    }
}
