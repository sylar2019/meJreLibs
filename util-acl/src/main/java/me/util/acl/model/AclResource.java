package me.util.acl.model;

import me.util.acl.AclResourceType;
import me.util.pojo.IDNamePojo;

/**
 * File Name             :
 *
 * @Author :  sylar
 * @Create :  2019-06-03
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
public interface AclResource extends IDNamePojo<Long> {

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
