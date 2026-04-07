package com.ruoyi.project_manage.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 项目信息VO
 *
 * @author ruoyi
 */
@Data
public class ProjectVO {

    /** 项目ID */
    private Long id;

    /** 项目名称 */
    private String name;

    /** 项目编号 */
    private String code;

    /** 项目负责人ID */
    private Long principalId;

    /** 项目负责人名称 */
    private String principalName;

    /** 项目启动时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 项目截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 项目状态：0-进行中 1-已归档 2-已作废 */
    private Integer status;

    /** 整体进度（0-100） */
    private Integer totalProgress;

    /** 项目描述 */
    private String description;

    /** 项目成员列表 */
    private List<ProjectMemberVO> members;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 创建人 */
    private String createBy;
}
