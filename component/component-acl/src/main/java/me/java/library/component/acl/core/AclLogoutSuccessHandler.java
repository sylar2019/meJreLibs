package me.java.library.component.acl.core;

import me.java.library.component.acl.AclService;
import me.java.library.component.acl.AclUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * File Name             :  CustomLogoutSuccessHandler
 *
 * @author :  sylar
 * @create :  2018/11/22
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
public class AclLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    AclService service;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        service.getEventHandler().onLogout(request, response, AclUtils.getLoginUser());
    }
}
