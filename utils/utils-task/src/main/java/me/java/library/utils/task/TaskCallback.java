package me.java.library.utils.task;

import me.java.library.common.Callback;

/**
 * File Name             :  TaskCallback
 *
 * @author :  sylar
 * Create                :  2019-11-24
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
public interface TaskCallback<T> extends Callback<T> {
    void onBegin(String taskName, Object[] args);

    void onEnd(String taskName, T result);

    void onTaskLog(String taskName, String log);
}
