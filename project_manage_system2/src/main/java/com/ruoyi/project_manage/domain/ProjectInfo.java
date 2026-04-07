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
import java.util.List;

/**
 * 项目信息实体
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("project_manage_info")
public class ProjectInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 项目ID */
    @TableId
    private Long id;

    /** 项目名称 */
    @NotBlank(message = "项目名称不能为空")
    @Size(max = 100, message = "项目名称长度不能超过100个字符")
    @Excel(name = "项目名称")
    private String name;

    /** 项目编号 */
    @NotBlank(message = "项目编号不能为空")
    @Size(max = 50, message = "项目编号长度不能超过50个字符")
    @Excel(name = "项目编号")
    private String code;

    /** 项目负责人ID */
    @NotNull(message = "项目负责人不能为空")
    @Excel(name = "项目负责人ID")
    private Long principalId;

    /** 项目负责人名称 */
    @TableField(exist = false)
    @Excel(name = "项目负责人")
    private String principalName;

    /** 项目启动时间 */
    @NotNull(message = "项目启动时间不能为空")
    @Excel(name = "启动时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 项目截止时间 */
    @NotNull(message = "项目截止时间不能为空")
    @Excel(name = "截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 项目状态：0-进行中 1-已归档 2-已作废 */
    @Excel(name = "项目状态", readConverterExp = "0=进行中,1=已归档,2=已作废")
    private Integer status;

    /** 整体进度（0-100） */
    @Excel(name = "整体进度")
    private Integer totalProgress;

    /** 项目描述 */
    @Size(max = 500, message = "项目描述长度不能超过500个字符")
    @Excel(name = "项目描述")
    private String description;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 项目成员ID列表（非数据库字段） */
    @TableField(exist = false)
    private List<Long> memberIds;

    /** 项目成员名称列表（非数据库字段） */
    @TableField(exist = false)
    private List<String> memberNames;

    /** 成员关联信息（非数据库字段） */
    @TableField(exist = false)
    private List<ProjectMember> members;
}
