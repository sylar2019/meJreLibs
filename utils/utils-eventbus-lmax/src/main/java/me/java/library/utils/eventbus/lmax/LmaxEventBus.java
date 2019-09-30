package me.java.library.utils.eventbus.lmax;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

/**
 * File Name             :  LmaxEventBus
 *
 * @Author :  sylar
 * @Create :  2019-09-01
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
public class LmaxEventBus {

    final static private int RING_BUFFER_SIZE = 1024 * 16;
    private Disruptor<LmaxEvent> disruptor;

    public void LmaxEventBus(final BaseLmaxEventHandler... handlers) {
        disruptor = new Disruptor<>(new LmaxEventFactory(),
                RING_BUFFER_SIZE,
                Executors.defaultThreadFactory());

        disruptor.setDefaultExceptionHandler(new LmaxEventHandlerException());
        disruptor.handleEventsWith(handlers);
        disruptor.start();
    }

    public void shutdown() {
        if (disruptor != null) {
            disruptor.shutdown();
            disruptor = null;
        }
    }

    public void publish(LmaxEventContent eventContent) {
        RingBuffer<LmaxEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent((event, sequence, data) -> event.setLmaxEventContent(data), eventContent);
    }


}
