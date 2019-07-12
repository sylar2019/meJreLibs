package me.util.misc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * File Name             :  JsonUtils
 * Author                :  sylar
 * Create Date           :  2018/4/10
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
public class JsonUtils {
    protected static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    private JsonUtils() {
    }

    public static String toJSONString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            return objectMapper.readValue(text, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getString(String text, String key) {
        try {
            JsonNode jsonNode = objectMapper.readValue(text, JsonNode.class);
            return jsonNode.get(key).textValue();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用于校验一个字符串是否是合法的JSON格式
     *
     * @param input
     * @return
     */
    public static boolean isValid(String input) {
        return new JsonValidator().validate(input);
    }

    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        String json = toJSONString(source);
        return parseObject(json, targetClass);
    }

    public static <T> List<T> copyList(List<?> source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        if (source.size() == 0) {
            return Lists.newArrayList();
        }

        return parseArray(toJSONString(source), targetClass);
    }

}
