package me.java.library.utils.task;

import me.java.library.utils.base.JsonUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Before;
import org.junit.Test;

/**
 * File Name             :  DemoTaskServiceTest
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
public class DemoTaskServiceTest {

    private DemoTaskService service;

    private TaskCallback<Foo> callback = new TaskCallback<Foo>() {
        @Override
        public void onBegin(String taskName, Object[] args) {
            System.out.println(String.format("[%s] begin. [args]:%s", taskName, JsonUtils.toJSONString(args)));
        }

        @Override
        public void onEnd(String taskName, Foo result) {
            System.out.println(String.format("[%s] end. [result]:%s", taskName, JsonUtils.toJSONString(result)));
        }

        @Override
        public void onTaskLog(String taskName, String log) {

        }


        @Override
        public void onSuccess(@Nullable Foo result) {
            System.out.println("callback result: " + result);
        }

        @Override
        public void onFailure(Throwable t) {
            System.err.println("callback exception:" + t.getCause());
        }
    };


    @Before
    public void onBefore() {
        service = new DemoTaskService();
    }

    @Test
    public void start() throws InterruptedException {
        service.start(callback, 10);
        System.out.println("started...");

        Thread.sleep(1000 * 10);
    }


}
