package me.java.library.component.acl.core;

import me.java.library.component.acl.AclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    AclService service;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) {
        service.getEventHandler().onAuthenticationFailure(request, response, exception);
    }
}
