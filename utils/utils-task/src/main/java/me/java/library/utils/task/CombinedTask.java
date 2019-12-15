package me.java.library.utils.task;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * File Name             :  CombinedTask
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
public abstract class CombinedTask<T> extends Task<T> {

    protected Task[] tasks;

    public CombinedTask(TaskContext<T> context, Task... tasks) {
        super(context);
        this.tasks = tasks;
    }

    protected abstract RecursiveTask<T> createInnerTask(Task[] tasks, Object... args);

    @Override
    protected T call(Object... args) throws Throwable {
        RecursiveTask<T> innerTask = createInnerTask(tasks, args);

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<T> future = pool.submit(innerTask);
        pool.shutdown();

        if (innerTask.isCompletedAbnormally()) {
            printThrowable(innerTask.getException());
            throw innerTask.getException();
        }

        return future.get();
    }
}
