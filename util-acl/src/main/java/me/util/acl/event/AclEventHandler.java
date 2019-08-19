package me.util.acl.event;

import me.util.acl.model.AclUser;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * File Name             :  AclEventHandler
 *
 * @Author :  sylar
 * @Create :  2019-08-13
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public interface AclEventHandler {

    /**
     * 用户登入
     *
     * @param request
     * @param response
     * @param user
     */
    void onLogin(HttpServletRequest request, HttpServletResponse response, AclUser user);

    /**
     * 用户登出
     *
     * @param request
     * @param response
     * @param user
     */
    void onLogout(HttpServletRequest request, HttpServletResponse response, AclUser user);


    /**
     * 认证过的用户访问无权限资源时
     *
     * @param request
     * @param response
     */
    void onAccessDenied(HttpServletRequest request, HttpServletResponse response, AclUser user);

    /**
     * 匿名用户访问无权限资源时
     *
     * @param request
     * @param response
     */
    void onAccessDeniedWithAnonymous(HttpServletRequest request, HttpServletResponse response);

    /**
     * session 过期失效时
     *
     * @param request
     * @param response
     * @param user
     */
    void onExpiredSession(HttpServletRequest request, HttpServletResponse response, AclUser user);

    /**
     * 发生认证异常时
     *
     * @param request
     * @param response
     * @param exception
     */
    void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception);

}
