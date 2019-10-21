package me.java.library.db.jpa.po;

import me.java.library.common.Identifiable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
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
public abstract class AbstractJpaEntityWithUuid extends AbstractJpaEntity implements Identifiable<String> {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }

        AbstractJpaEntityWithAutoId other = (AbstractJpaEntityWithAutoId) obj;
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
