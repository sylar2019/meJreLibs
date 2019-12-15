package me.java.library.utils.task;


import java.util.concurrent.RecursiveTask;

/**
 * File Name             :  SequentialTask
 *
 * @author :  sylar
 * Create                :  2019-11-21
 * Description           :  串行任务
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public class SequentialTask<T> extends CombinedTask<T> {
    public SequentialTask(TaskContext<T> context, Task... tasks) {
        super(context, tasks);
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

        @SuppressWarnings("unchecked")
        @Override
        protected T compute() {
            Object result = null;
            for (int i = 0; i < tasks.length; i++) {
                Task task = tasks[i];
                result = task.start(args);
                args = new Object[]{result};
            }

            return (T) result;
        }
    }
}
