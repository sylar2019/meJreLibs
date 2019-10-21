package me.java.library.common.chain;

import com.google.common.base.Preconditions;
import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * File Name             :  AbstractChainService
 * <p>
 * 基于Disruptor实现链式调用
 * 参见：https://github.com/LMAX-Exchange/disruptor/wiki/Getting-Started
 * <p>
 *
 * @author :  sylar
 * Create :  2019-10-20
 * Description           :  链式调用服务
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AbstractChainService implements ChainContainer {

    private final static int BUFFER_SIZE = 1024;
    private final static EventTranslatorVararg<ChainContext> TRANSLATOR = (event, sequence, args) -> event.setArgs(args);

    protected ChainCallback callback;
    protected Disruptor<ChainContext> disruptor = new Disruptor<>(
            ChainContext::new,
            BUFFER_SIZE,
            DaemonThreadFactory.INSTANCE
    );

    /**
     * 构造调用链
     * 参见：https://blog.csdn.net/KongZhongNiao/article/details/86083417
     *
     * @param disruptor
     * @param endTask
     */
    protected abstract void createChain(Disruptor<ChainContext> disruptor, EndTask endTask);

    public AbstractChainService(ChainCallback callback) {
        Preconditions.checkNotNull(callback);
        this.callback = callback;

        createChain(disruptor, new EndTask(this));
        disruptor.start();
    }

    @Override
    public void dispose() {
        start();
    }

    @Override
    public void start(Object... args) {
        disruptor.getRingBuffer().publishEvent(TRANSLATOR, args);
        onStarted();
    }

    @Override
    public void stop() {
        disruptor.shutdown();
        onStopped();
    }

    @Override
    public void onStarted() {
        callback.onStarted();
    }

    @Override
    public void onStopped() {
        callback.onStopped();
    }

    @Override
    public void onTaskCompleted(BaseTask task, Object result) {
        callback.onTaskCompleted(task, result);
    }

    @Override
    public void onTaskThrowable(BaseTask task, Throwable t) {
        callback.onTaskThrowable(task, t);
    }

    @Override
    public void onChainFinished(ChainContext context) {
        callback.onChainFinished(context);
    }


}
