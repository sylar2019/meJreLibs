package me.java.library.biz.acl;

import me.java.library.common.model.po.BaseEnum;

/**
 * File Name             :
 *
 * @author :  sylar
 * @create :  2018/10/4
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
public enum AclResourceType implements BaseEnum {

    //@formatter:off
    Menu     (1, "菜单"),
    Func     (2, "功能"),
    ;
    //@formatter:on

    private final int value;
    private final String name;

    AclResourceType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getValue() {
        return value;
    }

}
