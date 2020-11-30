package me.java.library.db.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import me.java.library.utils.base.JsonUtils;

import java.time.LocalDateTime;

@Getter
@Setter
public class Entity<T extends Entity<?>> extends Model<T> {

    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 逻辑删除标识
     */
    protected boolean deleted = false;

    /**
     * 启用状态标识
     */
    protected boolean enabled = true;

    /**
     * 扩展属性
     */
    protected String extented;

    /**
     * 描述
     */
    protected String description;

    /**
     * 创建人
     */
    protected String createBy;

    /**
     * 更新人
     */
    protected String updateBy;

    /**
     * 创建时间
     */
    protected LocalDateTime gmtCreate = LocalDateTime.now();

    /**
     * 更新时间
     */
    protected LocalDateTime gmtModified = LocalDateTime.now();

    @Override
    public String toString() {
        return JsonUtils.toJSONString(this);
    }
}
