package me.java.library.common.service;

/**
 * 初始化接口
 *
 * @author sylar
 */
public interface Initialization<T> {

    void init(T t, Object... params);

}
