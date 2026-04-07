package com.ruoyi.project_manage.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 任务概览VO
 *
 * @author ruoyi
 */
@Data
public class TaskOverviewVO {

    /** 任务ID */
    private Long taskId;

    /** 任务名称 */
    private String taskName;

    /** 任务编码 */
    private String taskCode;

    /** 项目ID */
    private Long projectId;

    /** 项目名称 */
    private String projectName;

    /** 责任人ID */
    private Long principalId;

    /** 责任人名称 */
    private String principalName;

    /** 计划开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planStartTime;

    /** 计划结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planEndTime;

    /** 进度 */
    private Integer progress;

    /** 状态 */
    private Integer status;

    /** 状态描述 */
    private String statusDesc;

    /** 预警等级 */
    private Integer warningLevel;

    /** 预警等级描述 */
    private String warningLevelDesc;
}
