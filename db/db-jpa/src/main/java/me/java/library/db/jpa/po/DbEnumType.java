package me.java.library.db.jpa.po;

import me.java.library.common.model.po.BaseEnum;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

/**
 * File Name             :  DbEnumType
 *
 * @author :  sylar
 * @create :  2018/10/4
 * Description           :  数据库枚举类型映射.
 * 枚举保存到数据库的是枚举的.getValue()的值，为Integer类型，数据库返回对象时需要把Integer转换枚举
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
@SuppressWarnings("unchecked")
public class DbEnumType implements UserType, DynamicParameterizedType {

    private static final int[] SQL_TYPES = new int[]{Types.INTEGER};
    private Class enumClass;

    @Override
    public void setParameterValues(Properties parameters) {
        final ParameterType reader = (ParameterType) parameters.get(PARAMETER_TYPE);
        if (reader != null) {
            enumClass = reader.getReturnedClass().asSubclass(Enum.class);
        }
    }

    //枚举存储int值
    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return enumClass;
    }

    //是否相等，不相等会触发JPA update操作
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == null && y == null) {
            return true;
        }
        return x != null && y != null && x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }

    //返回枚举
    @SuppressWarnings("unchecked")
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        if (value == null) {
            return null;
        }
        for (Object object : enumClass.getEnumConstants()) {
            if (Objects.equals(Integer.parseInt(value), ((BaseEnum) object).getValue())) {
                return object;
            }
        }
        throw new RuntimeException(String.format("Unknown name value [%s] for enum class [%s]", value, enumClass.getName()));
    }

    //保存枚举值
    @SuppressWarnings("unchecked")
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, SQL_TYPES[0]);
        } else if (value instanceof Integer) {
            st.setInt(index, (Integer) value);
        } else if (value instanceof BaseEnum) {
            st.setInt(index, ((BaseEnum) value).getValue());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        if (value instanceof Serializable) {
            return (Serializable) value;
        } else {
            return null;
        }
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
