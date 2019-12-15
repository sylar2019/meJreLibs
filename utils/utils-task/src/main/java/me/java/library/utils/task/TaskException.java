package me.java.library.utils.task;

/**
 * File Name             :  TaskException
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
public class TaskException extends RuntimeException {

    public TaskException() {
    }

    public TaskException(String message) {
        super(message);
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskException(Throwable cause) {
        super(cause);
    }

}
