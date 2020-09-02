package me.java.library.biz.acl.core;

import me.java.library.biz.acl.AclService;
import me.java.library.biz.acl.AclUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * File Name             :  AuthenticationSuccessHandlerImpl
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
public class AclSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    AclService service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        service.getEventHandler().onLogin(request, response, AclUtils.getLoginUser());
    }
}
