package me.java.library.db.jpa.po;

import me.java.library.common.model.pojo.IdName;

import javax.persistence.MappedSuperclass;

/**
 * @author :  sylar
 * @FileName :  AbstractJpaEntityWithUuidName
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
@MappedSuperclass
public abstract class AbstractJpaEntityWithUuidName extends AbstractJpaEntityWithUuid implements IdName<String> {

    protected String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
