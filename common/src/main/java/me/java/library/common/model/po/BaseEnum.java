package me.java.library.common.model.po;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * File Name             :  BaseEnum
 *
 * @author :  sylar
 * @create :  2018/10/4
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
@JsonDeserialize(using = BaseEnumDeserializer.class)
public interface BaseEnum extends Serializable {

    /**
     * 按枚举的value获取枚举实例
     *
     * @param enumType
     * @param value
     * @param <T>
     * @return
     */
    static <T extends BaseEnum> T fromValue(Class<T> enumType, int value) {
        for (T object : enumType.getEnumConstants()) {
            if (Objects.equals(value, object.getValue())) {
                return object;
            }
        }
        throw new IllegalArgumentException("No enum value " + value + " of " + enumType.getCanonicalName());
    }

    static <T extends BaseEnum> boolean equals(T t1, T t2) {
        return t1 != null && t2 != null && t1.getValue() == t2.getValue();
    }

    /**
     * IBaseDbEnum枚举转换成map
     *
     * @param enumType
     * @param <T>
     * @return
     */
    static <T extends BaseEnum> Map<Integer, String> toMap(Class<T> enumType) {
        Map<Integer, String> map = Maps.newHashMap();
        for (T t : enumType.getEnumConstants()) {
            map.put(t.getValue(), t.getName());
        }
        return map;
    }

    static <T extends BaseEnum> List<Integer> getValues(Class<T> enumType) {
        List<Integer> list = Lists.newArrayList();
        for (T t : enumType.getEnumConstants()) {
            list.add(t.getValue());
        }
        return list;
    }

    static <T extends BaseEnum> List<String> getNames(Class<T> enumType) {
        List<String> list = Lists.newArrayList();
        for (T t : enumType.getEnumConstants()) {
            list.add(t.getName());
        }
        return list;
    }

    static <T extends BaseEnum> boolean containsValue(Class<T> enumType, int value) {
        for (T t : enumType.getEnumConstants()) {
            if (value == t.getValue()) {
                return true;
            }
        }
        return false;
    }

    static <T extends BaseEnum> int getEnumIndex(Class<T> enumType, T enumInstance) {
        for (int i = 0; i < enumType.getEnumConstants().length; i++) {
            if (enumInstance == enumType.getEnumConstants()[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 用于显示的枚举名
     *
     * @return
     */
    String getName();

    /**
     * 存储到数据库的枚举值
     *
     * @return
     */
    @JsonValue
    int getValue();

}
