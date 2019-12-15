package me.java.library.utils.task;

/**
 * File Name             :  TaskContext
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
public interface TaskContext<T> {
    TaskCallback<T> getTaskCallback();
}
