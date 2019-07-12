package me.util.acl.core;

import me.util.acl.AclService;
import me.util.acl.AclUtils;
import me.util.acl.model.AclUser;
import me.util.pojo.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    AclService userService;
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("登录成功:" + authentication.getPrincipal());
        AclUser user = userService.getLoginUser();
        AclUtils.output(response, Result.newSuccess(user.toString()));
    }
}
