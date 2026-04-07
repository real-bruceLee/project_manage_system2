package com.ruoyi.project_manage.service;

import com.ruoyi.project_manage.domain.ProjectConfig;

import java.util.List;

/**
 * 系统配置Service接口
 *
 * @author ruoyi
 */
public interface IProjectConfigService {

    /**
     * 查询配置列表
     *
     * @return 配置列表
     */
    List<ProjectConfig> selectConfigList();

    /**
     * 根据ID查询配置
     *
     * @param id 配置ID
     * @return 配置信息
     */
    ProjectConfig selectConfigById(Long id);

    /**
     * 根据配置键查询配置值
     *
     * @param configKey 配置键
     * @return 配置值
     */
    String selectConfigValueByKey(String configKey);

    /**
     * 新增配置
     *
     * @param config 配置信息
     * @return 结果
     */
    int insertConfig(ProjectConfig config);

    /**
     * 修改配置
     *
     * @param config 配置信息
     * @return 结果
     */
    int updateConfig(ProjectConfig config);

    /**
     * 删除配置
     *
     * @param ids 配置ID数组
     * @return 结果
     */
    int deleteConfigByIds(Long[] ids);

    /**
     * 校验配置键是否唯一
     *
     * @param configKey 配置键
     * @return 结果
     */
    boolean checkConfigKeyUnique(String configKey);
}
