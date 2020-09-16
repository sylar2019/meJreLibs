package me.java.library.db.jpa;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import me.java.library.utils.base.ReflectUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * File Name             :  JpaUtils
 *
 * @author :  sylar
 * Create :  2018/10/5
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
public class JpaUtils {

    public final static String DbEnumTypeName = "DbEnumType";

    /**
     * object[] to dto
     * <p>
     * 约定规则：
     * 1、object[] 对应 一个 dto
     * 2、dto 类须有默认的无参构造方法
     * 3、源字段个数须小于等于目标类型属性个数（不得大于）
     * 4、源与目标对应顺序的含义须一致，且源字段类型能转换成目标属性类型
     *
     * @param list
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz) {
        if (list == null || list.size() == 0 || clazz == null) {
            return Lists.newArrayList();
        }

        List<Field> fields = ReflectUtils.getAllFieldByOrder(clazz);
        int srcFieldCount = list.get(0).length;
        int dstFieldCount = fields.size();

        Preconditions.checkState(srcFieldCount <= dstFieldCount,
                "源数据与目标类型的属性个数不相符");

        List<T> resut = Lists.newArrayList();
        try {
            for (Object[] o : list) {
                T t = clazz.newInstance();
                for (int i = 0; i < fields.size() && i < srcFieldCount; i++) {
                    fields.get(i).setAccessible(true);
                    fields.get(i).set(t, o[i]);
                }
                resut.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Preconditions.checkState(list.size() == resut.size(), "object[] to dto 转换失败");
        return resut;
    }

}
