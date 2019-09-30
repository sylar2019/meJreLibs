package me.java.library.db.jpa;

/**
 * File Name             :  ForeignKeyService
 *
 * @author :  sylar
 * @create :  2018/11/13
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
public interface ForeignKeyService {

    /**
     * 注册外键关系
     *
     * @param primaryEntityClass 主表实体类型
     * @param slaveEntityClass   从表实体类型
     * @param foreignKeyName     从表外键字段名称
     */
    void registeForeignKey(Class<?> primaryEntityClass,
                           Class<?> slaveEntityClass,
                           String foreignKeyName);

    /**
     * 当主表记录删除时，检查外键依赖
     *
     * @param primaryEntityClass
     * @param id
     */
    void checkOnDeletePrimaryEntity(Class<?> primaryEntityClass, Object id);
}
