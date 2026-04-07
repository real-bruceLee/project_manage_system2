package com.ruoyi.project_manage.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.project_manage.domain.ProjectConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置Mapper接口
 *
 * @author ruoyi
 */
public interface ProjectConfigMapper extends BaseMapperPlus<ProjectConfigMapper, ProjectConfig, ProjectConfig> {

    /**
     * 根据配置键查询配置
     *
     * @param configKey 配置键
     * @return 配置信息
     */
    ProjectConfig selectByConfigKey(@Param("configKey") String configKey);
}
