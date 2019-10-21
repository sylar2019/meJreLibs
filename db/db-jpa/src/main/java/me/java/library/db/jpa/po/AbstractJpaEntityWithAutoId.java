package me.java.library.db.jpa.po;

import me.java.library.common.Identifiable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author :  sylar
 * @FileName :  AbstractJpaEntityWithAutoId
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
public abstract class AbstractJpaEntityWithAutoId extends AbstractJpaEntity implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || id == null || !getClass().equals(obj.getClass())) {
            return false;
        }

        Identifiable other = (Identifiable) obj;
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
