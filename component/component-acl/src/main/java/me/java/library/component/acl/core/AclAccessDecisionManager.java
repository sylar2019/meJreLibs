package me.java.library.component.acl.core;

import com.google.common.base.Strings;
import me.java.library.component.acl.AclConst;
import me.java.library.component.acl.AclMode;
import me.java.library.component.acl.AclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * File Name             :
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
public class AclAccessDecisionManager implements AccessDecisionManager {

    @Autowired
    protected AclService aclService;

    @Override
    public void decide(Authentication authentication,
                       Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        if (aclService.getAclMode().equals(AclMode.None)) {
            return;
        }

        if (authentication == null
                || authentication.getPrincipal() == null
                || AclConst.ANONYMOUS.equals(authentication.getPrincipal())) {
            //匿名用户
            throw new AccessDeniedException("权限不足!");
        }

        if (aclService.getAclMode().equals(AclMode.ByLogin)) {
            return;
        }

        //当前用户所拥有的所有角色,信息存储在Authorities中
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute configAttribute : configAttributes) {
            //当前请求需要的权限
            String needRole = configAttribute.getAttribute();
            if (Strings.isNullOrEmpty(needRole)) {
                //needRole为空，则不需要权限，放行
                return;
            }

            //有角色要求，须按角色权限检查的请求
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)) {
                    // 说明此URL地址符合权限,可以放行
                    return;
                }
            }
        }

        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
