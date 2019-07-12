package me.util.data.jpa;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.util.misc.ExceptionUtils;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.persister.walking.spi.AttributeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

/**
 * File Name             :  ForeignKeyServiceImpl
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
@Component("foreignKeyService")
public class ForeignKeyServiceImpl implements ForeignKeyService {
    private final static Logger log = LoggerFactory.getLogger(ForeignKeyServiceImpl.class);

    @Autowired
    private EntityManager entityManager;

    private Map<Class<?>, EntityAttr> entityAttrMap = Maps.newHashMap();
    private Map<Class<?>, List<ForeignKeyItem>> foreignKeyMap = Maps.newHashMap();

    @PostConstruct
    private void onInit() {
        buildEntityAttrMap();
    }

    @Override
    public void registeForeignKey(Class<?> primaryEntityClass,
                                  Class<?> slaveEntityClass,
                                  String foreignKeyName) {
        Preconditions.checkNotNull(primaryEntityClass);
        Preconditions.checkNotNull(slaveEntityClass);
        Preconditions.checkNotNull(foreignKeyName);
        checkForeignKeyName(slaveEntityClass, foreignKeyName);

        List<ForeignKeyItem> foreignKeyItems;
        if (!foreignKeyMap.containsKey(primaryEntityClass)) {
            foreignKeyItems = Lists.newArrayList();
            foreignKeyMap.put(primaryEntityClass, foreignKeyItems);
        }

        foreignKeyItems = foreignKeyMap.get(primaryEntityClass);
        foreignKeyItems.add(new ForeignKeyItem(entityAttrMap.get(slaveEntityClass).getTableName(), foreignKeyName));
    }

    @Override
    public void checkOnDeletePrimaryEntity(Class<?> primaryEntityClass, Object id) {
        Preconditions.checkNotNull(primaryEntityClass);
        Preconditions.checkNotNull(id);

        if (!foreignKeyMap.containsKey(primaryEntityClass)) {
            //未注册外键的实体，无需检查
            return;
        }

        foreignKeyMap.get(primaryEntityClass).forEach(item -> {
            //统计在从表中的引用数
            String table = item.getSlaveTableName();
            String column = item.getForeignKeyName();
            int count = countRefrence(id, table, column);
            Preconditions.checkState(count == 0,
                    String.format("该记录被引用，不允许删除\r\n(ID值:%s,在%s表%s列存在引用)", id, table, column));
        });

    }


    private int countRefrence(Object idValue, String table, String... columns) {
        if (idValue == null || table == null || columns == null || columns.length == 0) {
            return 0;
        }

        StringBuilder sb = new StringBuilder(String.format("select count(*) from %s where deleted = 0 ", table));
        sb.append(String.format(" and (%s = '%s' ", columns[0], idValue));
        for (int i = 1; i < columns.length; i++) {
            sb.append(String.format(" or %s = '%s'", columns[i], idValue));
        }
        sb.append(")");

        try {
            Object obj = entityManager.createNativeQuery(sb.toString()).getSingleResult();
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            String errMsg = String.format("检查外键依赖时发生错误！sql:%s", sb.toString());
            log.error(errMsg);

            ExceptionUtils.throwException(errMsg);

            return 1;
        }
    }


    private void buildEntityAttrMap() {
        //通过EntityManager获取factory
        EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) entityManagerFactory.unwrap(SessionFactory.class);
        Map<String, EntityPersister> persisterMap = sessionFactory.getEntityPersisters();
        for (Map.Entry<String, EntityPersister> entity : persisterMap.entrySet()) {
            Class targetClass = entity.getValue().getMappedClass();
            SingleTableEntityPersister persister = (SingleTableEntityPersister) entity.getValue();
            Iterable<AttributeDefinition> attributes = persister.getAttributes();

            //Entity的名称
            String entityName = targetClass.getSimpleName();
            //Entity对应的表的英文名
            String tableName = persister.getTableName();

            EntityAttr entityAttr = new EntityAttr(targetClass);
            entityAttr.setEntityName(entityName);
            entityAttr.setTableName(tableName);
            Map<String, PropertyAttr> propertyAttrMap = Maps.newHashMap();
            entityAttr.setPropertyAttrMap(propertyAttrMap);

            //属性
            for (AttributeDefinition attr : attributes) {
                //在entity中的属性名称
                String propertyName = attr.getName();
                //对应数据库表中的字段名
                String columnName = persister.getPropertyColumnNames(propertyName)[0];
                String type = "";
                PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(targetClass, propertyName);
                if (targetPd != null) {
                    type = targetPd.getPropertyType().getSimpleName();
                }

                propertyAttrMap.put(columnName, new PropertyAttr(propertyName, columnName, type));
            }

            entityAttrMap.put(targetClass, entityAttr);
        }
    }


    private void checkForeignKeyName(Class<?> slaveEntityClass, String foreignKeyName) {
        Preconditions.checkState(entityAttrMap.containsKey(slaveEntityClass),
                "无效的从表实体类型:" + slaveEntityClass.getSimpleName());

        EntityAttr entityAttr = entityAttrMap.get(slaveEntityClass);
        Preconditions.checkState(entityAttr.containsColumn(foreignKeyName),
                String.format("%s表中不存在如此字段:%s", entityAttr.getTableName(), foreignKeyName));
    }

    class ForeignKeyItem {
        private String slaveTableName;
        private String foreignKeyName;

        public ForeignKeyItem(String slaveTableName, String foreignKeyName) {
            this.slaveTableName = slaveTableName;
            this.foreignKeyName = foreignKeyName;
        }

        public String getSlaveTableName() {
            return slaveTableName;
        }

        public void setSlaveTableName(String slaveTableName) {
            this.slaveTableName = slaveTableName;
        }

        public String getForeignKeyName() {
            return foreignKeyName;
        }

        public void setForeignKeyName(String foreignKeyName) {
            this.foreignKeyName = foreignKeyName;
        }
    }

    class EntityAttr {
        private Class<?> entityClass;
        private String entityName;
        private String tableName;
        private Map<String, PropertyAttr> propertyAttrMap;

        public EntityAttr(Class<?> entityClass) {
            this.entityClass = entityClass;
        }

        public boolean containsColumn(String columnName) {
            return propertyAttrMap.containsKey(columnName);
        }


        public Class<?> getEntityClass() {
            return entityClass;
        }

        public void setEntityClass(Class<?> entityClass) {
            this.entityClass = entityClass;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public Map<String, PropertyAttr> getPropertyAttrMap() {
            return propertyAttrMap;
        }

        public void setPropertyAttrMap(Map<String, PropertyAttr> propertyAttrMap) {
            this.propertyAttrMap = propertyAttrMap;
        }
    }

    class PropertyAttr {
        private String propertyName;
        private String propertyType;
        private String columnName;

        public PropertyAttr(String propertyName, String propertyType, String columnName) {
            this.propertyName = propertyName;
            this.propertyType = propertyType;
            this.columnName = columnName;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public String getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(String propertyType) {
            this.propertyType = propertyType;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }
    }
}
