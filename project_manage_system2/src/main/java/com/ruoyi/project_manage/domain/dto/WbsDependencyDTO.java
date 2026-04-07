package com.ruoyi.project_manage.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * WBS依赖关系DTO
 *
 * @author ruoyi
 */
@Data
public class WbsDependencyDTO {

    /** 依赖ID */
    private Long id;

    /** 前置任务ID */
    @NotNull(message = "前置任务ID不能为空")
    private Long preTaskId;

    /** 依赖类型：FS-完成-开始、SS-开始-开始、FF-完成-完成、SF-开始-完成 */
    private String dependType;

    /** 延隔天数 */
    private Integer delayDays;
}
