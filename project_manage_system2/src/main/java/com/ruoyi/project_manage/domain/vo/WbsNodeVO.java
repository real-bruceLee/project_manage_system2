package com.ruoyi.project_manage.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * WBS节点VO
 *
 * @author ruoyi
 */
@Data
public class WbsNodeVO {

    /** 节点ID */
    private Long id;

    /** 项目ID */
    private Long projectId;

    /** WBS编码 */
    private String code;

    /** 节点名称 */
    private String name;

    /** 节点类型：stage-阶段、milestone-里程碑、task-任务、sub_task-子任务 */
    private String type;

    /** 父节点ID */
    private Long parentId;

    /** 排序号 */
    private Integer sortOrder;

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

    /** 实际开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date actualStartTime;

    /** 实际结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date actualEndTime;

    /** 工期（天） */
    private Integer duration;

    /** 进度（0-100） */
    private Integer progress;

    /** 任务状态 */
    private Integer status;

    /** 任务描述 */
    private String description;

    /** 子节点列表 */
    private List<WbsNodeVO> children;

    /** 前置任务列表 */
    private List<WbsDependencyVO> preTasks;

    /** 层级 */
    private Integer level;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 创建人 */
    private String createBy;
}
