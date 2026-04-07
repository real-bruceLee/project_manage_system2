package com.ruoyi.project_manage.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 变更申请实体
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("project_manage_change")
public class ChangeRequest extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 变更ID */
    @TableId
    private Long id;

    /** 项目ID */
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    /** 变更单号 */
    @NotBlank(message = "变更单号不能为空")
    @Size(max = 50, message = "变更单号长度不能超过50个字符")
    @Excel(name = "变更单号")
    private String changeNo;

    /** 变更类型：plan-计划变更、progress-进度变更、other-其他变更 */
    @Excel(name = "变更类型", readConverterExp = "plan=计划变更,progress=进度变更,other=其他变更")
    private String changeType;

    /** 变更等级：1-一般变更 2-重大变更 */
    @Excel(name = "变更等级", readConverterExp = "1=一般变更,2=重大变更")
    private Integer changeLevel;

    /** 关联WBS节点ID */
    private Long wbsId;

    /** WBS节点名称（非数据库字段） */
    @TableField(exist = false)
    private String wbsName;

    /** 变更标题 */
    @NotBlank(message = "变更标题不能为空")
    @Size(max = 100, message = "变更标题长度不能超过100个字符")
    @Excel(name = "变更标题")
    private String title;

    /** 变更内容 */
    @Excel(name = "变更内容")
    private String content;

    /** 变更原因 */
    @Size(max = 500, message = "变更原因长度不能超过500个字符")
    @Excel(name = "变更原因")
    private String reason;

    /** 影响分析 */
    @Size(max = 500, message = "影响分析长度不能超过500个字符")
    @Excel(name = "影响分析")
    private String impact;

    /** 原值 */
    @Size(max = 500, message = "原值长度不能超过500个字符")
    @Excel(name = "原值")
    private String oldValue;

    /** 新值 */
    @Size(max = 500, message = "新值长度不能超过500个字符")
    @Excel(name = "新值")
    private String newValue;

    /** 审批状态：0-草稿 1-审批中 2-已通过 3-已驳回 4-已撤回 */
    @Excel(name = "审批状态", readConverterExp = "0=草稿,1=审批中,2=已通过,3=已驳回,4=已撤回")
    private Integer status;

    /** 申请人ID */
    @NotNull(message = "申请人不能为空")
    private Long applicantId;

    /** 申请人名称（非数据库字段） */
    @TableField(exist = false)
    private String applicantName;

    /** 申请时间 */
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 审批人ID */
    private Long approverId;

    /** 审批人名称（非数据库字段） */
    @TableField(exist = false)
    private String approverName;

    /** 审批时间 */
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;

    /** 审批意见 */
    @Size(max = 500, message = "审批意见长度不能超过500个字符")
    @Excel(name = "审批意见")
    private String approveOpinion;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;
}
