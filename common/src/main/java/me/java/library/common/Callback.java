package me.java.library.common;

import com.google.common.util.concurrent.FutureCallback;

/**
 * 回调接口，通知成功或失败
 *
 * @param <Result>
 * @author sylar
 */
public interface Callback<Result> extends FutureCallback<Result> {

}