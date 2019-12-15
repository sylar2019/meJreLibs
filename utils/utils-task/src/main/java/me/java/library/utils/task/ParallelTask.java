package me.java.library.utils.task;


import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * File Name             :  SequentialTask
 *
 * @author :  sylar
 * Create                :  2019-11-21
 * Description           :  并行任务
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public class ParallelTask<T> extends CombinedTask<T> {

    private ParallelMergeListener<T> mergeListener;

    public ParallelTask(TaskContext<T> context, ParallelMergeListener<T> mergeListener, Task... tasks) {
        super(context, tasks);
        this.mergeListener = mergeListener;
    }

    @Override
    protected RecursiveTask<T> createInnerTask(Task[] tasks, Object... args) {
        return new InnerTask(tasks, args);
    }

    class InnerTask extends RecursiveTask<T> {

        private Task[] tasks;
        private Object[] args;

        InnerTask(Task[] tasks, Object[] args) {
            this.tasks = tasks;
            this.args = args;
        }

        @Override
        protected T compute() {
            List<TaskWrap> wraps = Lists.newArrayList();
            for (Task task : tasks) {
                wraps.add(new TaskWrap(task, args));
            }

            for (TaskWrap task : wraps) {
                task.fork();
            }

            Object[] results = new Object[wraps.size()];
            for (int i = 0; i < wraps.size(); i++) {
                TaskWrap task = wraps.get(i);
                results[i] = task.join();
            }

            return mergeListener.merge(results);
        }
    }

    class TaskWrap extends RecursiveTask {

        private Task task;
        private Object[] args;

        TaskWrap(Task task, Object... args) {
            this.task = task;
            this.args = args;
        }

        @Override
        protected Object compute() {
            return task.start(args);
        }
    }
}
