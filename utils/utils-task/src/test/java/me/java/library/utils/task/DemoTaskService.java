package me.java.library.utils.task;


/**
 * File Name             :  DemoTaskService
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
public class DemoTaskService extends AbstractTaskService<Foo> {

    private ParallelMergeListener<Foo> mergeListener = results -> {
        int value = 0;
        for (Object obj : results) {
            if (obj instanceof Foo) {
                Foo res = (Foo) obj;
                value += res.getValue();
            }
        }
        return new Foo(value);
    };

    @Override
    protected Task<Foo> createRoadmap() {

        Task1 t1 = new Task1(this);
        Task2 t2 = new Task2(this);
        Task3 t3 = new Task3(this);
        Task4 t4 = new Task4(this);
        Task5 t5 = new Task5(this);


        //串行
//        return new SequentialTask<>(t2);
        return new SequentialTask<>(this, t1, t2, t3);

        //并行
//        return new ParallelTask<>(mergeListener, t1,t2,t3);

        //混杂模式
//        SequentialTask<Foo> s1 = new SequentialTask<>(t1, t2);
//        SequentialTask<Foo> s2 = new SequentialTask<>(t3, t4);
//        ParallelTask<Foo> p1 = new ParallelTask<>(mergeListener, s1, s2);
//        SequentialTask<Foo> s3 = new SequentialTask<>(p1, t5);
//        return s3;
    }
}
