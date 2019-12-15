package me.java.library.utils.task;


/**
 * File Name             :  Task1
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
public class Task1 extends Task<Foo> {
    public Task1(TaskContext<Foo> context) {
        super(context);
    }

    @Override
    protected Foo call(Object... args) throws Throwable {
        int value = 0;
        if (args != null && args.length > 0) {
            Object arg = args[0];
            if (arg instanceof Integer) {
                value = (Integer) arg;
            }
            if (arg instanceof Foo) {
                value = ((Foo) arg).getValue();
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Foo(value + 1);
    }
}
