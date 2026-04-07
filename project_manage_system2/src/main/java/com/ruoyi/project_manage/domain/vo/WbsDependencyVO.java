package com.ruoyi.project_manage.domain.vo;

import lombok.Data;

/**
 * WBS依赖关系VO
 *
 * @author ruoyi
 */
@Data
public class WbsDependencyVO {

    /** 依赖ID */
    private Long id;

    /** 当前任务ID */
    private Long taskId;

    /** 前置任务ID */
    private Long preTaskId;

    /** 前置任务名称 */
    private String preTaskName;

    /** 前置任务WBS编码 */
    private String preTaskCode;

    /** 依赖类型：FS-完成-开始、SS-开始-开始、FF-完成-完成、SF-开始-完成 */
    private String dependType;

    /** 依赖类型描述 */
    private String dependTypeDesc;

    /** 延隔天数 */
    private Integer delayDays;
}
