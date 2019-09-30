package me.java.library.utils.base;


import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventUtils {

    static EventBus bus = new EventBus();

    static {
        bus.register(new DeadEventListener());
    }

    public static void regist(Object listener) {
        bus.register(listener);
    }

    public static void unregist(Object listener) {
        bus.unregister(listener);
    }

    public static void postEvent(final Object event) {
        bus.post(event);
    }

    static public class DeadEventListener {

        @Subscribe
        @AllowConcurrentEvents
        public void listen(DeadEvent event) {
            System.out.println(String.format("###DeadEvent###\nevent:%s",
                    event.getEvent().getClass().getName()));

        }
    }
}

