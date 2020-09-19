package me.java.library.biz.acl;

/**
 * File Name             :  AclConst
 *
 * @author :  sylar
 * @create :  2018/11/18
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
public interface AclConst {

    String CONTENT_TYPE = "application/json;charset=UTF-8";

    /**
     * 白名单：即不在权限定义内的资源请求，都允许访问
     * 黑名单：即不在权限定义内的资源请求，都禁止访问
     */
    String ROLE_LOGIN = "ROLE_LOGIN";
    String ROLE_ADMIN = "ROLE_ADMIN";
    String ROLE_USER = "ROLE_USER";
    String USER_ADMIN = "admin";


    //匿名用户
    String ANONYMOUS = "anonymousUser";
    String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
}
