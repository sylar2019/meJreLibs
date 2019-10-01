package me.java.library.common.service;

/**
 * 初始化接口
 *
 * @author sylar
 */
public interface Initialization<Context> {

    void init(Context context, Object... params);

}
