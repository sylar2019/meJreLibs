package me.java.library.biz.acl.config;

import me.java.library.biz.acl.AclMode;
import me.java.library.biz.acl.AclService;
import me.java.library.biz.acl.core.*;
import me.java.library.biz.acl.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * File Name             :  SecurityConfig
 *
 * @author :  sylar
 * @create :  2018/11/15
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
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public abstract class AbstractSecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String LOGIN_PAGE = "/login";
    private final static String LOGOUT_PAGE = "/logout";

    @Autowired
    protected AclService aclService;
    @Autowired
    protected AclMetadataSource aclMetadataSource;
    @Autowired
    protected AclAccessDecisionManager aclAccessDecisionManager;
    @Autowired
    protected AclAccessDeniedHandler aclAccessDeniedHandler;
    @Autowired
    protected AclSuccessHandler aclSuccessHandler;
    @Autowired
    protected AclFailureHandler aclFailureHandler;
    @Autowired
    protected AclLogoutSuccessHandler aclLogoutSuccessHandler;
    @Autowired
    protected AclAuthenticationEntryPoint aclAuthenticationEntryPoint;
    @Autowired
    protected AclSessionExpiredStrategy aclSessionExpiredStrategy;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题,此处只能放静态资源，后端的请求不可在此忽略
        //前后端分离开发模式，不用设置此项
        //web.ignoring().antMatchers("/**/css/*.css", "/static/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(aclService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return aclService.getPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        if (aclService.getAclMode().equals(AclMode.None)) {
            //匹配所有请求都不需要权限控制
            http.authorizeRequests().anyRequest().access("permitAll");
            return;
        }


        //自定义的权限验证过滤器，取代系统默认的XML式过滤器
        //使用自定义的过滤器后， @EnableGlobalMethodSecurity支持的3类注解都将无效
        http.formLogin()
                .loginPage(getLoginPage())
                .successHandler(aclSuccessHandler)
                .failureHandler(aclFailureHandler)
                .permitAll().and();
        http.logout()
                .logoutUrl(getLogoutPage())
                .logoutRequestMatcher(new AntPathRequestMatcher(getLogoutPage()))
                .invalidateHttpSession(false)
                .logoutSuccessHandler(aclLogoutSuccessHandler).permitAll().and();
        http.exceptionHandling()
                .authenticationEntryPoint(aclAuthenticationEntryPoint)
                .accessDeniedHandler(aclAccessDeniedHandler).and();
        http.sessionManagement()
                //最大session并发数量
                .maximumSessions(Integer.MAX_VALUE)
                //false之后登录踢掉之前登录,true则不允许之后登录
                .maxSessionsPreventsLogin(false)
                //登录被踢掉时的自定义操作
                .expiredSessionStrategy(aclSessionExpiredStrategy);
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(aclMetadataSource);
                        o.setAccessDecisionManager(aclAccessDecisionManager);
                        return o;
                    }
                }).and();
    }


    protected String getLoginPage() {
        return LOGIN_PAGE;
    }

    protected String getLogoutPage() {
        return LOGOUT_PAGE;
    }
}
