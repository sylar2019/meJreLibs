package me.java.library.utils.base;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
@SuppressWarnings("unchecked")
public class ReflectUtils {

    static public <T> List<Field> getAllField(Class<T> clazz) {
        List<Field> fieldList = Lists.newArrayList();
        Class tempClass = clazz;
        while (tempClass != null) {
            //当父类为null的时候说明到达了最上层的父类(Object类)
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            //得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        return fieldList;
    }

    static public <T> List<Field> getAllFieldByOrder(Class<T> clazz) {
        List<Field> list = Lists.newArrayList();
        Class tempClass = clazz;
        while (tempClass != null) {
            list.addAll(0, Lists.newArrayList(tempClass.getDeclaredFields()));
            //得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    static public <T> T getFieldValue(Object obj, String fieldName) {
        assert obj != null;
        Class<?> clazz = obj.getClass();
        try {
            return (T) clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    static public void setFieldValue(Object obj, String fieldName, Object fieldValue) {
        assert obj != null;
        assert fieldName != null;
        Class<?> clazz = obj.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.set(obj, fieldValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    static public <T> T getReflectObj(String classFullName) {
        Preconditions.checkNotNull(classFullName, "classFullName is null");
        Object obj = null;

        try {
            Class<?> c = Class.forName(classFullName);
            Constructor<?>[] constructors = c.getConstructors();
            if (constructors.length > 0) {
                //有默认构造方法
                obj = c.newInstance();
            } else {
                //单子模式
                Method method = c.getMethod("getInstance");
                if (Modifier.isStatic(method.getModifiers())) {
                    obj = method.invoke(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T) obj;
    }

    static public <T> Class<?> getGenericType(List<T> list) {
        return list.toArray().getClass().getComponentType();
    }

    static public <T> Class<?> getGenericType(Collection<T> collection) {
        return Lists.newArrayList(collection).toArray().getClass().getComponentType();
    }

    static public Class<?> getGenericType(Object obj) {
        return getGenericType(obj, 0);
    }

    static public Class<?> getGenericType(Object obj, int index) {
        if (obj == null) {
            return null;
        }

        // 获取泛型父类
        Type type = obj.getClass().getGenericSuperclass();

        // 不是泛型类
        if (!(type instanceof ParameterizedType)) {
            return null;
        }

        ParameterizedType genType = (ParameterizedType) type;
        Type[] types = genType.getActualTypeArguments();

        Preconditions.checkState(index >= 0 && index < types.length);
        Type objType = types[index];

        return (Class<?>) objType;
    }
}
