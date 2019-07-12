package me.util.acl.core;

import me.util.acl.AclUtils;
import me.util.pojo.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * File Name             :  AuthenticationFailureHandlerImpl
 *
 * @author :  sylar
 * @create :  2018/11/16
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
@Component
public class AclFailureHandler implements AuthenticationFailureHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.info("登陆失败");
        String errMsg = "登录失败!";
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
        }

        AclUtils.output(response, Result.newFaild(errMsg));
    }
}
