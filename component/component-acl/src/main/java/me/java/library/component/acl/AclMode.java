package me.java.library.component.acl;

import me.java.library.common.IBaseDbEnum;

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
public enum AclMode implements IBaseDbEnum {

    //@formatter:off
    None        (0, "无认证模式","无需认证"),
    ByLogin     (1, "认证模式","仅认证登录"),
    ByRole      (2, "角色模式","以角色授权"),
    ;
    //@formatter:on

    private final int value;
    private final String name;
    private final String description;

    AclMode(int value, String name, String description) {
        this.value = value;
        this.name = name;
        this.description = description;
    }


    @Override
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getValue() {
        return value;
    }

}
