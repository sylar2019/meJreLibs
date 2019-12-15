package me.java.library.utils.task;

/**
 * File Name             :  ParallelMergeListener
 *
 * @author :  sylar
 * Create                :  2019-11-22
 * Description           :  并行任务合并计算
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) allthings.vip  All Rights Reserved
 * *******************************************************************************************
 */
public interface ParallelMergeListener<T> {

    T merge(Object... results);

}
