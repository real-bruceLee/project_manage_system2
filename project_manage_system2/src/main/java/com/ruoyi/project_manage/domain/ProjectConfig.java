package com.ruoyi.project_manage.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 系统配置实体
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("project_manage_config")
public class ProjectConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 配置ID */
    @TableId
    private Long id;

    /** 配置键 */
    @NotBlank(message = "配置键不能为空")
    @Size(max = 100, message = "配置键长度不能超过100个字符")
    private String configKey;

    /** 配置值 */
    @Size(max = 500, message = "配置值长度不能超过500个字符")
    private String configValue;

    /** 配置说明 */
    @Size(max = 200, message = "配置说明长度不能超过200个字符")
    private String configDesc;
}
