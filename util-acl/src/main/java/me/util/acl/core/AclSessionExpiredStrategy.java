package me.util.acl.core;

import me.util.acl.AclUtils;
import me.util.pojo.dto.Result;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * File Name             :  CustomSessionInformationExpiredStrategy
 *
 * @Author :  sylar
 * @Create :  2018/12/13
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

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        //定制session超时的响应
        AclUtils.output(event.getResponse(), Result.newFaild(-1, "并发登录，当前登录中止"));
    }
}
