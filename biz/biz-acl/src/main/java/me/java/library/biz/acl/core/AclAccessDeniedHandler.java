package me.java.library.biz.acl.core;

import me.java.library.biz.acl.AclService;
import me.java.library.biz.acl.AclUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * File Name             :  AuthAccessDeniedHandler
 *
 * @author :  sylar
 * @create :  2018/11/17
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
public class AclAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    AclService service;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        service.getEventHandler().onAccessDenied(request, response, AclUtils.getLoginUser());
    }
}
