package me.java.library.component.flowable;

import java.text.MessageFormat;

/**
 * @description: 描述支持的流程引擎版本
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public final class Version {

    private static final int major = 1;

    private static final int minor = 0;

    private static final int patch = 0;

    private Version() {
    }

    /**
     * 获取兼容的flowable引擎版本
     *
     * @return 兼容的flowable引擎版本
     */
    public static String getSupportVersion() {
        return "6.4.1.0";
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public static String getVersion() {
        return MessageFormat.format("{0}.{1}.{2}", major, minor, patch);
    }
}
