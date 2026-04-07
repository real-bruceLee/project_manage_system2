package com.ruoyi.project_manage.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * WBS节点DTO
 *
 * @author ruoyi
 */
@Data
public class WbsNodeDTO {

    /** 节点ID */
    private Long id;

    /** 项目ID */
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    /** WBS编码 */
    @NotBlank(message = "WBS编码不能为空")
    @Size(max = 50, message = "WBS编码长度不能超过50个字符")
    private String code;

    /** 节点名称 */
    @NotBlank(message = "节点名称不能为空")
    @Size(max = 100, message = "节点名称长度不能超过100个字符")
    private String name;

    /** 节点类型：stage-阶段、milestone-里程碑、task-任务、sub_task-子任务 */
    @NotBlank(message = "节点类型不能为空")
    private String type;

    /** 父节点ID */
    private Long parentId;

    /** 排序号 */
    private Integer sortOrder;

    /** 责任人ID */
    @NotNull(message = "责任人不能为空")
    private Long principalId;

    /** 计划开始时间 */
    private Date planStartTime;

    /** 计划结束时间 */
    private Date planEndTime;

    /** 实际开始时间 */
    private Date actualStartTime;

    /** 实际结束时间 */
    private Date actualEndTime;

    /** 工期（天） */
    private Integer duration;

    /** 进度（0-100） */
    private Integer progress;

    /** 任务状态 */
    private Integer status;

    /** 任务描述 */
    @Size(max = 500, message = "任务描述长度不能超过500个字符")
    private String description;

    /** 前置任务列表 */
    private List<WbsDependencyDTO> preTasks;
}
