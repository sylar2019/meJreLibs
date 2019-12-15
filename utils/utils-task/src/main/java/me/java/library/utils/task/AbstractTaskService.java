package me.java.library.utils.task;

import me.java.library.common.service.ConcurrentService;

/**
 * File Name             :  AbstractTaskService
 *
 * @author :  sylar
 * Create                :  2019-11-22
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
public abstract class AbstractTaskService<T> implements TaskContext<T> {

    protected Task<T> mainTask;
    protected TaskCallback<T> callback;

    public void start(TaskCallback<T> callback, Object... args) {
        this.callback = callback;

        ConcurrentService.getInstance().postRunnable(() -> {
            mainTask = createRoadmap();
            try {
                callback.onSuccess(mainTask.start(args));
            } catch (TaskException e) {
                callback.onFailure(e);
            }
        });
    }

    protected abstract Task<T> createRoadmap();

    @Override
    public TaskCallback<T> getTaskCallback() {
        return callback;
    }
}
