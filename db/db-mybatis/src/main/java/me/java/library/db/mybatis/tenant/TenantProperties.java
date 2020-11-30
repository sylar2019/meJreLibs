package me.java.library.db.mybatis.tenant;


import com.baomidou.mybatisplus.core.toolkit.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 租户属性
 */
@Getter
@Setter
@ConfigurationProperties(prefix = Constants.MYBATIS_PLUS + ".tenant")
public class TenantProperties {

    /**
     * 是否开启租户模式
     */
    private Boolean enable = false;

    /**
     * 需要排除的多租户的表
     */
    private List<String> ignoreTables = Arrays.asList(
            "sys_user",
            "sys_depart",
            "sys_role",
            "sys_tenant",
            "sys_role_permission"
    );

    /**
     * 多租户字段名称
     */
    private String column = "tenant_id";

    /**
     * 排除不进行租户隔离的sql
     * 样例全路径：vip.mate.system.mapper.UserMapper.findList
     */
    private List<String> ignoreSqls = new ArrayList<>();
}


