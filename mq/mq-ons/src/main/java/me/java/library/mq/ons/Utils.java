package me.java.library.mq.ons;

import com.google.common.base.Joiner;

/**
 * @author : sylar
 * @fullName : me.java.library.mq.ons.Utils
 * @createDate : 2020/8/26
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class Utils {

    public static String tagsFromArray(String... tags) {
        String subExpression = "*";
        if (tags != null && tags.length > 0) {
            subExpression = Joiner.on("||").skipNulls().join(tags);
        }
        return subExpression;
    }
}
