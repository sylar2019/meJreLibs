package me.java.library.common.service;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import me.java.library.common.Callback;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * File Name             :  ConcurrentServiceTest
 *
 * @Author :  sylar
 * @Create :  2019-10-19
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class ConcurrentServiceTest {

    ConcurrentService service = ConcurrentService.getInstance();

    Callable<Integer> callable = () -> {
        print("do somthing on background");
        Thread.sleep(1000 * 1);
        return 100;
    };

    Callback<Integer> callback = new Callback<Integer>() {
        @Override
        public void onSuccess(Integer integer) {
            print("Result: " + integer);
        }

        @Override
        public void onFailure(Throwable t) {
            print("Throwable: " + t);
        }
    };

    FutureCallback<Integer> futureCallback = new FutureCallback<Integer>() {
        @Override
        public void onSuccess(@Nullable Integer result) {
            print("Result: " + result);
        }

        @Override
        public void onFailure(Throwable t) {
            print("Throwable: " + t);
        }
    };


    @Test
    public void syncPost1() {
        print("begin post");
        SettableFuture future = service.syncPost(callable, callback);
        print("exit");
    }

    @Test
    public void syncPost2() {
        print("begin post");
        SettableFuture future = service.syncPost(callable, callback, 1L, TimeUnit.SECONDS);

        print("exit");
    }

    @Test
    public void syncPost3() {
        print("begin post");
        SettableFuture future = service.syncPost(callable, callback, 1L, TimeUnit.SECONDS, 6);
        print("exit");
    }


    @Test
    public void postRunnable1() throws InterruptedException {
        print("begin post");
        ListenableFuture future = service.postRunnable(() -> {
            try {
                print("do somthing...");
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        while (!future.isDone()) {
            print("wait result...");
            Thread.sleep(1000);
        }
        print("exit");
    }


    @Test
    public void postRunnable2() throws InterruptedException {
        print("begin post");
        ListenableFuture future = service.postRunnable(() -> {
            try {
                print("do somthing...");
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, new FutureCallback<Object>() {
            @Override
            public void onSuccess(@Nullable Object result) {
                print("task over");
            }

            @Override
            public void onFailure(Throwable t) {
                print("Throwable: " + t);
            }
        });

        while (!future.isDone()) {
            print("wait result...");
            Thread.sleep(1000);
        }
        print("exit");
    }

    @Test
    public void postCallable1() throws InterruptedException {
        print("begin post");
        ListenableFuture future = service.postCallable(callable);

        while (!future.isDone()) {
            print("wait result...");
            Thread.sleep(1000);
        }

        print("exit");
    }


    @Test
    public void postCallable2() throws InterruptedException {
        print("begin post");
        ListenableFuture<Integer> future = service.postCallable(callable, futureCallback);

        while (!future.isDone()) {
            print("wait result...");
            Thread.sleep(1000);
        }
        print("exit");
    }

    @Test
    public void postCallable3() throws InterruptedException {
        print("begin post");

        SettableFuture<Integer> settableFuture = SettableFuture.create();

        ListenableFuture<Integer> future = service.postCallable(() -> {
                    print("client request...");
                    return settableFuture.get(1, TimeUnit.SECONDS);
                },
                callback,
                1L,
                TimeUnit.SECONDS,
                6,
                new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        // 距离上一次重试的时间间隔
                        System.out.println("距上一次重试的间隔时间为:" + attempt.getDelaySinceFirstAttempt());
                        // 重试次数
                        System.out.println("重试次数: " + attempt.getAttemptNumber());
                        // 重试过程是否有异常
                        System.out.println("重试过程是否有异常:" + attempt.hasException());
                        if (attempt.hasException()) {
                            System.out.println("异常的原因:" + attempt.getExceptionCause().toString());
                        }
                        //重试正常返回的结果
                        System.out.println("重试结果为:" + attempt.hasResult());

                        System.out.println();
                    }
                });

        service.postRunnable(() -> {
            print("server working...");
            try {
                Thread.sleep(5000);
                settableFuture.set(100);
                print("server working over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        while (!future.isDone()) {
            print("wait result...");
            Thread.sleep(1000);
        }

        print("exit");
    }

    private void print(String txt) {
        System.out.println(txt);
    }


}