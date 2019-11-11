package me.java.library.component.acl;

import me.java.library.component.acl.event.AclEventHandler;
import me.java.library.component.acl.event.DefaultAclEventHandler;
import me.java.library.component.acl.model.AclResource;
import me.java.library.component.acl.model.AclRole;
import me.java.library.component.acl.model.AclUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * File Name             :
 *
 * @author :  sylar
 * Create :  2019-06-03
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
public interface AclService extends UserDetailsService {

    /**
     * 认证授权模式
     *
     * @return
     */
    default AclMode getAclMode() {
        return AclMode.ByRole;
    }

    /**
     * 默认加密算法
     *
     * @return
     */
    default PasswordAlgorithm getDefaulAlgorithm() {
        return PasswordAlgorithm.bcrypt;
    }

    default PasswordEncoder getPasswordEncoder() {
        DelegatingPasswordEncoder encoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        encoder.setDefaultPasswordEncoderForMatches(getDefaulAlgorithm().getEncoder());
        return encoder;
    }

    /**
     * ACL事件处理器
     *
     * @return
     */
    default AclEventHandler getEventHandler() {
        return new DefaultAclEventHandler();
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    default AclUser getLoginUser() {
        return AclUtils.getLoginUser();
    }

    /**
     * 获取对资源有权限的角色列表
     *
     * @param resourceId 资源ID
     * @return 角色列表
     */
    List<AclRole> findRolesByResource(Long resourceId);

    /**
     * 获取所有资源（按url倒序排列）
     *
     * @return 资源列表
     */
    List<AclResource> findAllByOrderByUrlDesc();


}
