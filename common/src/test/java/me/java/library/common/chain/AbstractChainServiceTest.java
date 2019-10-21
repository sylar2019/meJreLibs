package me.java.library.common.chain;

import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import org.junit.Before;
import org.junit.Test;

/**
 * File Name             :  AbstractChainServiceTest
 *
 * @Author :  sylar
 * Create                :  2019-10-22
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
public class AbstractChainServiceTest {

    private DemoChainService service;

    private ExceptionHandler exceptionHandler = new ExceptionHandler() {
        @Override
        public void handleEventException(Throwable ex, long sequence, Object event) {
            print(String.format("【handleEventException】sequence：%d, ex: %s", sequence, ex));
        }

        @Override
        public void handleOnStartException(Throwable ex) {
            print(String.format("【handleOnStartException】ex: %s", ex));
        }

        @Override
        public void handleOnShutdownException(Throwable ex) {
            print(String.format("【handleOnShutdownException】ex: %s", ex));
        }
    };

    private ChainCallback callback = new ChainCallback() {
        @Override
        public void onStarted() {
            print("chain started...");
        }

        @Override
        public void onStopped() {
            print("chain stopped...");
        }

        @Override
        public void onChainFinished(ChainContext context) {
            print(String.format("【onChainFinished】 context: %s", context));
        }

        @Override
        public void onTaskCompleted(BaseTask task, Object result) {
            print(String.format("【onTaskCompleted】task:%s,result:%s", task.getTaskCode(), result));
        }

        @Override
        public void onTaskThrowable(BaseTask task, Throwable t) {
            print(String.format("【onTaskThrowable】task:%s,error:%s", task.getTaskCode(), t));
        }
    };

    @Before
    public void onBefore() {
        service = new DemoChainService(callback);
    }

    @Test
    public void start() throws InterruptedException {
        service.start();
        Thread.sleep(1000 * 5);
        print("exit");
    }

    @Test
    public void stop() {
        service.stop();
    }

    private void print(String txt) {
        System.out.println(txt);
    }

    class DemoChainService extends AbstractChainService {

        public DemoChainService(ChainCallback callback) {
            super(callback);
        }

        @Override
        protected void createChain(Disruptor<ChainContext> disruptor, EndTask endTask) {
            BaseTask t1 = new BaseTask1(this);
            BaseTask t2 = new BaseTask2(this);
            BaseTask t3 = new BaseTask3(this);
            BaseTask t4 = new BaseTask4(this);
            BaseTask t5 = new BaseTask5(this);

//            //串行1
            disruptor.handleEventsWith(t1).handleEventsWith(t2).handleEventsWith(t3);
//            //串行2
//            disruptor.handleEventsWith(t1).then(t2).then(t3);
//            //并行1
//            disruptor.handleEventsWith(t1, t2, t3);
//            //并行2
//            disruptor.handleEventsWith(t1);
//            disruptor.handleEventsWith(t2);
//            disruptor.handleEventsWith(t3);
//            //菱形1
//            disruptor.handleEventsWith(t1, t2).then(t3);
//            //菱形2
//            disruptor.handleEventsWith(t1, t2).handleEventsWith(t3);
//            //六边形
//            disruptor.handleEventsWith(t1, t2);
//            disruptor.after(t1).handleEventsWith(t3);
//            disruptor.after(t2).handleEventsWith(t4);
//            disruptor.after(t3, t4).handleEventsWith(t5);
//            //set ExceptionHandler
//            disruptor.setDefaultExceptionHandler(exceptionHandler);
//            disruptor.handleExceptionsFor(t1).with(exceptionHandler);
//            disruptor.handleExceptionsFor(t2).with(exceptionHandler);

            disruptor.after(t3).then(endTask);
        }

    }

    class BaseTask1 extends BaseTask {
        public BaseTask1(ChainContainer container) {
            super(container);
        }

        @Override
        protected Object onEvent(ChainContext context) throws Exception {
            Thread.sleep(500);
//            throw new Exception("exception from " + getClass().getSimpleName());
            print("task finished: " + getClass().getSimpleName());
            return 100;
        }
    }

    class BaseTask2 extends BaseTask {
        public BaseTask2(ChainContainer container) {
            super(container);
        }

        @Override
        protected Object onEvent(ChainContext context) throws Exception {
            Thread.sleep(500);
//            throw new Exception("exception from " + getClass().getSimpleName());
            print("task finished: " + getClass().getSimpleName());
            return 200;
        }
    }

    class BaseTask3 extends BaseTask {
        public BaseTask3(ChainContainer container) {
            super(container);
        }

        @Override
        protected Object onEvent(ChainContext context) throws Exception {
            Thread.sleep(500);
            print("task finished: " + getClass().getSimpleName());
            return 300;
        }
    }

    class BaseTask4 extends BaseTask {
        public BaseTask4(ChainContainer container) {
            super(container);
        }

        @Override
        protected Object onEvent(ChainContext context) throws Exception {
            Thread.sleep(500);
            print("task finished: " + getClass().getSimpleName());
            return 400;
        }
    }

    class BaseTask5 extends BaseTask {
        public BaseTask5(ChainContainer container) {
            super(container);
        }

        @Override
        protected Object onEvent(ChainContext context) throws Exception {
            Thread.sleep(500);
            print("task finished: " + getClass().getSimpleName());
            return 500;
        }
    }

}