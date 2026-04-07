package com.ruoyi.project_manage.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * WBS任务依赖关系实体
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("project_manage_wbs_dependency")
public class WbsDependency extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 依赖ID */
    @TableId
    private Long id;

    /** 当前任务ID */
    @NotNull(message = "任务ID不能为空")
    private Long taskId;

    /** 前置任务ID */
    @NotNull(message = "前置任务ID不能为空")
    private Long preTaskId;

    /** 依赖类型：FS-完成-开始、SS-开始-开始、FF-完成-完成、SF-开始-完成 */
    private String dependType;

    /** 延隔天数 */
    private Integer delayDays;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;
}
