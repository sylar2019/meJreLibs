package me.java.library.biz.acl.core;

import me.java.library.biz.acl.AclService;
import me.java.library.biz.acl.AclUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * File Name             :  CustomSessionInformationExpiredStrategy
 *
 * @author :  sylar
 * Create :  2018/12/13
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
public class AclSessionExpiredStrategy implements SessionInformationExpiredStrategy {

    @Autowired
    AclService service;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        //定制session超时的响应
        service.getEventHandler().onExpiredSession(event.getRequest(), event.getResponse(), AclUtils.getLoginUser());
    }
}
