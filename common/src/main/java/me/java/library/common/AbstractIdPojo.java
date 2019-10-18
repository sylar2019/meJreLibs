package me.java.library.common;

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
public abstract class AbstractIdPojo<ID> extends AbstractPojo implements IDPojo<ID> {

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
    public final int hashCode() {
        return super.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compareTo(IDPojo<ID> o) {

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
