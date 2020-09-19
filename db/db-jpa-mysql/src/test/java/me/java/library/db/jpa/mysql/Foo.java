package me.java.library.db.jpa.mysql;

import me.java.library.db.jpa.po.AbstractJpaEntityWithAutoId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * File Name             :  MongoFoo
 * Author                :  sylar
 * Create Date           :  2018/4/15
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
@Table
@Entity
public class Foo extends AbstractJpaEntityWithAutoId {

    @Column
    private String name;

    @Column
    private int count;

    public String getName() {
        return name;
    }

    public Foo setName(String name) {
        this.name = name;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Foo setCount(int count) {
        this.count = count;
        return this;
    }


}
