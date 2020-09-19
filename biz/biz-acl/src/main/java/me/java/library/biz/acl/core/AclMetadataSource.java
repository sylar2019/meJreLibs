package me.java.library.biz.acl.core;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import me.java.library.biz.acl.*;
import me.java.library.biz.acl.model.AclResource;
import me.java.library.biz.acl.model.AclRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * File Name             :  UrlFilterInvocationSecurityMetadataSource
 *
 * @author :  sylar
 * @create :  2018/11/17
 * Description           :  该类的主要功能就是通过当前的请求地址，获取该地址需要的用户角色
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
public class AclMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    AclService aclService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        if (aclService.getAclMode().equals(AclMode.None)) {
            //不需要权限
            return null;
        }

        if (aclService.getAclMode().equals(AclMode.ByLogin)) {
            //需要登录
            return SecurityConfig.createList(AclConst.ROLE_LOGIN);
        }

        if (aclService.getAclMode().equals(AclMode.ByRole)) {
            //根据角色授予权限
            FilterInvocation filterInvocation = (FilterInvocation) object;
            //获取请求地址
            String requestUrl = filterInvocation.getRequestUrl();
            boolean isGetMethod = Objects.equals(HttpMethod.GET.toString(), filterInvocation.getHttpRequest().getMethod());
            List<AclResource> resources = aclService.findAllByOrderByUrlDesc();
            for (AclResource resource : resources) {
                List<AclRole> roles = aclService.findRolesByResource(resource.getId());
                if (roles.size() > 0
                        && !Strings.isNullOrEmpty(resource.getUrl())
                        && resource.getAclResourceType() == AclResourceType.Func) {
                    String resUrl = AclUtils.trimResourceUrl(resource.getUrl());
                    if (isGetMethod) {
                        resUrl = resUrl + "**";
                        if (antPathMatcher.matchStart(resUrl, requestUrl)) {
                            return buildAttrs(roles);
                        }
                    } else if (antPathMatcher.match(resUrl, requestUrl)) {
                        return buildAttrs(roles);
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private List<ConfigAttribute> buildAttrs(List<AclRole> roles) {
        List<String> nameList = Lists.newArrayList();
        roles.forEach(role -> nameList.add(role.getName()));

        String[] roleNames = nameList.toArray(new String[0]);
        return SecurityConfig.createList(roleNames);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
