package com.ruoyi.project_manage.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 项目信息DTO
 *
 * @author ruoyi
 */
@Data
public class ProjectDTO {

    /** 项目ID */
    private Long id;

    /** 项目名称 */
    @NotBlank(message = "项目名称不能为空")
    @Size(max = 100, message = "项目名称长度不能超过100个字符")
    private String name;

    /** 项目编号 */
    @NotBlank(message = "项目编号不能为空")
    @Size(max = 50, message = "项目编号长度不能超过50个字符")
    private String code;

    /** 项目负责人ID */
    @NotNull(message = "项目负责人不能为空")
    private Long principalId;

    /** 项目启动时间 */
    @NotNull(message = "项目启动时间不能为空")
    private Date startTime;

    /** 项目截止时间 */
    @NotNull(message = "项目截止时间不能为空")
    private Date endTime;

    /** 项目描述 */
    @Size(max = 500, message = "项目描述长度不能超过500个字符")
    private String description;

    /** 项目成员ID列表 */
    private List<Long> memberIds;
}
