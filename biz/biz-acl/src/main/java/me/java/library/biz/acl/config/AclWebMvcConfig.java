package me.java.library.biz.acl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * File Name             :  WebMvcConfig
 *
 * @author :  sylar
 * @create :  2018/11/17
 * Description           :    当前后端分离时,无需配置
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
@Configuration
public class AclWebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        当前后端分离时，无需addViewController
//        registry.addViewController("/base/user/login").setViewName("login");
//        registry.addViewController(AbstractSecurityConfig.SESSION_TIMEOUT_URL).setViewName("timeout");
    }
}
