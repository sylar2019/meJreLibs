package me.java.library.utils.disruptor.event;

import com.google.common.collect.Lists;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import me.java.library.common.service.ConcurrentService;

import java.util.List;

/**
 * File Name             :  DisruptorEventService
 *
 * @author :  sylar
 * Create                :  2019-10-21
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
@SuppressWarnings("ALL")
public class DisruptorEventService implements EventService {
    private final static int BUFFER_SIZE = 1024;
    private final static EventTranslatorTwoArg<DisruptorEvent, Object, Object> TRANSLATOR = (event, sequence, arg0, arg1) -> {
        event.setSource(arg0);
        event.setContent(arg1);
    };

    protected List<EventListener> listeners = Lists.newArrayList();

    protected Disruptor<DisruptorEvent> disruptor = new Disruptor<>(
            DisruptorEvent::new,
            BUFFER_SIZE,
            DaemonThreadFactory.INSTANCE
    );

    private ExceptionHandler exceptionHandler = new ExceptionHandler() {
        @Override
        public void handleEventException(Throwable ex, long sequence, Object event) {
            System.err.println(String.format("###handleEventException###\nevent:%s\nerror:%s", event, ex));
        }

        @Override
        public void handleOnStartException(Throwable ex) {
            System.err.println(String.format("###handleOnStartException###\nerror:%s", ex));
        }

        @Override
        public void handleOnShutdownException(Throwable ex) {
            System.err.println(String.format("###handleOnShutdownException###\nerror:%s", ex));
        }
    };

    public DisruptorEventService() {
        disruptor.setDefaultExceptionHandler(exceptionHandler);
        disruptor.handleEventsWith(
                (EventHandler<DisruptorEvent>) (event, sequence, endOfBatch) -> onDisruptorEvent(event)
        );
    }

    @Override
    public void dispose() {
        stop();
    }

    @Override
    public void regist(EventListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }

        if (listeners.size() > 0) {
            start();
        }
    }

    @Override
    public void unregist(EventListener listener) {
        listeners.remove(listener);
        if (listeners.size() == 0) {
            stop();
        }
    }

    @Override
    public void postEvent(Event<?, ?> event) {
        disruptor.publishEvent(TRANSLATOR, event.getSource(), event.getContent());
    }

    private void start() {
        disruptor.start();
    }

    private void stop() {
        disruptor.shutdown();
    }

    private void onDisruptorEvent(DisruptorEvent event) {
        listeners.forEach(listener -> ConcurrentService.getInstance().postRunnable(() -> listener.onEvent(event)));
    }
}
