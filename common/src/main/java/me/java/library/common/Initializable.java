package me.java.library.common;

/**
 * 初始化接口
 *
 * @author sylar
 */
public interface Initializable<T> {
    void init(T context, Object... params);
}
