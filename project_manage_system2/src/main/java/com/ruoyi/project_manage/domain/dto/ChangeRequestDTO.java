package com.ruoyi.project_manage.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 变更申请DTO
 *
 * @author ruoyi
 */
@Data
public class ChangeRequestDTO {

    /** 变更ID */
    private Long id;

    /** 项目ID */
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    /** 变更类型：plan-计划变更、progress-进度变更、other-其他变更 */
    private String changeType;

    /** 变更等级：1-一般变更 2-重大变更 */
    private Integer changeLevel;

    /** 关联WBS节点ID */
    private Long wbsId;

    /** 变更标题 */
    @NotBlank(message = "变更标题不能为空")
    @Size(max = 100, message = "变更标题长度不能超过100个字符")
    private String title;

    /** 变更内容 */
    private String content;

    /** 变更原因 */
    @Size(max = 500, message = "变更原因长度不能超过500个字符")
    private String reason;

    /** 影响分析 */
    @Size(max = 500, message = "影响分析长度不能超过500个字符")
    private String impact;

    /** 原值 */
    @Size(max = 500, message = "原值长度不能超过500个字符")
    private String oldValue;

    /** 新值 */
    @Size(max = 500, message = "新值长度不能超过500个字符")
    private String newValue;

    /** 审批意见 */
    @Size(max = 500, message = "审批意见长度不能超过500个字符")
    private String approveOpinion;
}
