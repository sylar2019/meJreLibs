package me.java.library.biz.acl.event;

import me.java.library.biz.acl.AclUtils;
import me.java.library.biz.acl.model.AclUser;
import me.java.library.common.model.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * File Name             :  DefaultAclEventHandler
 *
 * @author :  sylar
 * Create :  2019-08-13
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
public class DefaultAclEventHandler implements AclEventHandler {
    protected final static int RESULT_CODE_UNAUTH = -1;
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onLogin(HttpServletRequest request, HttpServletResponse response, AclUser user) {
        log.info("用户登入:" + user);
        AclUtils.output(response, Result.newSuccess(user.toString()));
    }

    @Override
    public void onLogout(HttpServletRequest request, HttpServletResponse response, AclUser user) {
        log.info("用户登出:" + user);
        AclUtils.output(response, Result.newSuccess());
    }

    @Override
    public void onAccessDenied(HttpServletRequest request, HttpServletResponse response, AclUser user) {
        log.info("权限不足:" + user);
        AclUtils.output(response, Result.newFaild("权限不足，请联系管理员!"));
    }

    @Override
    public void onAccessDeniedWithAnonymous(HttpServletRequest request, HttpServletResponse response) {
        log.info("匿名用户无权限");
        AclUtils.output(response, Result.newFaild(RESULT_CODE_UNAUTH, "未登录"));
    }

    @Override
    public void onExpiredSession(HttpServletRequest request, HttpServletResponse response, AclUser user) {
        log.info("会话超时:" + user);
        AclUtils.output(response, Result.newFaild(RESULT_CODE_UNAUTH, "并发登录，当前登录中止"));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

        String errMsg;
        if (exception instanceof BadCredentialsException ||
                exception instanceof UsernameNotFoundException) {
            errMsg = "账户名或者密码输入错误!";
        } else if (exception instanceof LockedException) {
            errMsg = "账户被锁定，请联系管理员!";
        } else if (exception instanceof CredentialsExpiredException) {
            errMsg = "密码过期，请联系管理员!";
        } else if (exception instanceof AccountExpiredException) {
            errMsg = "账户过期，请联系管理员!";
        } else if (exception instanceof DisabledException) {
            errMsg = "账户被禁用，请联系管理员!";
        } else {
            errMsg = "未知异常";
        }

        log.info("认证失败:" + errMsg);
        AclUtils.output(response, Result.newFaild(errMsg));
    }
}
