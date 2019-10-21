package me.java.library.common.model.pojo;

import me.java.library.common.Identifiable;

import java.util.concurrent.atomic.AtomicLong;

/**
 * File Name             :  AbstractIdPojo
 *
 * @author :  sylar
 * @create :  2018/10/7
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
public abstract class AbstractIdPojo<ID> extends AbstractPojo implements Identifiable<ID> {

    protected static final AtomicLong uniqueIdGenerator = new AtomicLong();
    protected final long uniquifier = uniqueIdGenerator.getAndIncrement();
    protected ID id;

    @Override
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }

        Identifiable<ID> other = (Identifiable<ID>) obj;
        return id.equals(other.getId());
    }

    @Override
    public int compareTo(Identifiable<ID> o) {

        if (this == o) {
            return 0;
        }

        @SuppressWarnings("unchecked")
        AbstractIdPojo<ID> other = (AbstractIdPojo) o;
        int returnCode;

        returnCode = hashCode() - other.hashCode();
        if (returnCode != 0) {
            return returnCode;
        }

        if (uniquifier < other.uniquifier) {
            return -1;
        }
        if (uniquifier > other.uniquifier) {
            return 1;
        }

        throw new Error("failed to compare two different pojo");
    }
}
