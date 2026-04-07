package com.ruoyi.project_manage.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 变更申请VO
 *
 * @author ruoyi
 */
@Data
public class ChangeRequestVO {

    /** 变更ID */
    private Long id;

    /** 项目ID */
    private Long projectId;

    /** 项目名称 */
    private String projectName;

    /** 变更单号 */
    private String changeNo;

    /** 变更类型：plan-计划变更、progress-进度变更、other-其他变更 */
    private String changeType;

    /** 变更类型描述 */
    private String changeTypeDesc;

    /** 变更等级：1-一般变更 2-重大变更 */
    private Integer changeLevel;

    /** 变更等级描述 */
    private String changeLevelDesc;

    /** 关联WBS节点ID */
    private Long wbsId;

    /** WBS节点名称 */
    private String wbsName;

    /** 变更标题 */
    private String title;

    /** 变更内容 */
    private String content;

    /** 变更原因 */
    private String reason;

    /** 影响分析 */
    private String impact;

    /** 原值 */
    private String oldValue;

    /** 新值 */
    private String newValue;

    /** 审批状态：0-草稿 1-审批中 2-已通过 3-已驳回 4-已撤回 */
    private Integer status;

    /** 审批状态描述 */
    private String statusDesc;

    /** 申请人ID */
    private Long applicantId;

    /** 申请人名称 */
    private String applicantName;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 审批人ID */
    private Long approverId;

    /** 审批人名称 */
    private String approverName;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;

    /** 审批意见 */
    private String approveOpinion;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 创建人 */
    private String createBy;
}
