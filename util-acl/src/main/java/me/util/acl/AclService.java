package me.util.acl;

import me.util.acl.model.AclResource;
import me.util.acl.model.AclRole;
import me.util.acl.model.AclUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * File Name             :
 *
 * @Author :  sylar
 * @Create :  2019-06-03
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
    AclMode getAclMode();

    /**
     * 获取所有角色
     *
     * @return
     */
    List<AclRole> getAllRoles();

    /**
     * 获取用户的角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<AclRole> findRolesByUser(Long userId);

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

    /**
     * 获取角色拥有权限的资源列表
     *
     * @param roleId 角色ID
     * @return 资源列表
     */
    List<AclResource> getResourcesByRole(Long roleId);

    /**
     * 获取角色对应的用户列表
     *
     * @param roleId 角色列表
     * @return 用户列表
     */
    List<AclUser> getUsersByRole(Long roleId);

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    AclUser getLoginUser();

    /**
     * 通过登录名(username)查找用户
     *
     * @return 用户
     */
    AclUser findByUsername(String username);

}
