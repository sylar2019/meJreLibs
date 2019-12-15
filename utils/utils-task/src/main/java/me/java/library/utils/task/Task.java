package me.java.library.utils.task;


import me.java.library.common.service.ConcurrentService;
import me.java.library.utils.base.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File Name             :  Task
 *
 * @author :  sylar
 * Create                :  2019-11-21
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
public abstract class Task<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected TaskContext<T> context;
    protected Object[] args;

    public Task(TaskContext<T> context) {
        this.context = context;
    }

    public T start(Object... args) throws TaskException {
        this.args = args;
        onBegin(args);

        printThread();
        printArgs();
        long time = System.currentTimeMillis();

        T result;

        try {
            result = call(args);
        } catch (Throwable e) {
            printThrowable(e);
            throw new TaskException(e);
        }

        printResult(result);
        printDuration(System.currentTimeMillis() - time);

        onEnd(result);
        return result;
    }

    protected abstract T call(Object... args) throws Throwable;

    public Object[] getArgs() {
        return args;
    }

    protected void onBegin(Object[] args) {
        if (context.getTaskCallback() != null) {
            ConcurrentService.getInstance().postRunnable(() ->
                    context.getTaskCallback().onBegin(getTaskName(), args));
        }
    }

    protected void onEnd(T result) {
        if (context != null) {
            ConcurrentService.getInstance().postRunnable(() ->
                    context.getTaskCallback().onEnd(getTaskName(), result));
        }
    }


    protected void printThread() {
        print(String.format("【%s】: %s",
                getTaskName(),
                Thread.currentThread().toString()));
    }

    protected void printArgs() {
        print(String.format("【%s】args: %s",
                getTaskName(),
                JsonUtils.toJSONString(args)));
    }

    protected void printDuration(long duration) {
        print(String.format("【%s】耗时: %s",
                getTaskName(),
                duration));
    }

    protected void printResult(Object result) {
        print(String.format("【%s】result: %s",
                getTaskName(),
                JsonUtils.toJSONString(result)));
    }

    protected void printThrowable(Throwable t) {
        if (t != null) {
            print(String.format("【%s】task throwable: %s",
                    getTaskName(),
                    t.getCause()));
            t.printStackTrace();
        }
    }

    protected void print(Object obj) {
        if (obj != null) {
            logger.info(obj.toString());
        }
    }

    protected String getTaskName() {
        return getClass().getSimpleName();
    }
}
