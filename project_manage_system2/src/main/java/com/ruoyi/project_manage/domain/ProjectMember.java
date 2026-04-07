package com.ruoyi.project_manage.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目成员关联实体
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("project_manage_member")
public class ProjectMember extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 关联ID */
    @TableId
    private Long id;

    /** 项目ID */
    private Long projectId;

    /** 成员用户ID */
    private Long userId;

    /** 成员名称（非数据库字段） */
    @TableField(exist = false)
    private String userName;

    /** 成员昵称（非数据库字段） */
    @TableField(exist = false)
    private String nickName;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;
}
