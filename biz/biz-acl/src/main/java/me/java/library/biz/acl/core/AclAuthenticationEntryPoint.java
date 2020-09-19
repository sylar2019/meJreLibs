package me.java.library.biz.acl.core;

import me.java.library.biz.acl.AclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * File Name             :  CustomAuthenticationEntryPoint
 *
 * @author :  sylar
 * @create :  2018/12/4
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
public class AclAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    AclService service;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //匿名用户访问无权限资源时的异常
        service.getEventHandler().onAccessDeniedWithAnonymous(request, response);
    }
}
