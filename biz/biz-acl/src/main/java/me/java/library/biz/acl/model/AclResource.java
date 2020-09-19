package me.java.library.biz.acl.model;

import me.java.library.biz.acl.AclResourceType;
import me.java.library.common.model.pojo.IdName;

/**
 * File Name             :
 *
 * @author :  sylar
 * Create :  2019-06-03
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
public interface AclResource extends IdName<Long> {

    /**
     * 资源类型
     *
     * @return
     */
    AclResourceType getAclResourceType();

    /**
     * 与前端组件对应
     */
    String getCode();

    /**
     * 路径匹配规则,形如：/admin/**
     */
    String getUrl();

    /**
     * 前端图标
     */
    String getIcon();

}
