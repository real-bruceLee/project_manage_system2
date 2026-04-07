package com.ruoyi.project_manage.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project_manage.domain.ProjectConfig;
import com.ruoyi.project_manage.mapper.ProjectConfigMapper;
import com.ruoyi.project_manage.service.IProjectConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 系统配置Service实现
 *
 * @author ruoyi
 */
@Slf4j
@Service
public class ProjectConfigServiceImpl implements IProjectConfigService {

    @Autowired
    private ProjectConfigMapper projectConfigMapper;

    @Override
    public List<ProjectConfig> selectConfigList() {
        return projectConfigMapper.selectList(null);
    }

    @Override
    public ProjectConfig selectConfigById(Long id) {
        return projectConfigMapper.selectById(id);
    }

    @Override
    public String selectConfigValueByKey(String configKey) {
        ProjectConfig config = projectConfigMapper.selectByConfigKey(configKey);
        return config != null ? config.getConfigValue() : null;
    }

    @Override
    public int insertConfig(ProjectConfig config) {
        // 校验配置键唯一性
        if (!checkConfigKeyUnique(config.getConfigKey())) {
            throw new ServiceException("配置键已存在");
        }

        config.setCreateBy(SecurityUtils.getUsername());
        config.setCreateTime(new Date());

        int rows = projectConfigMapper.insert(config);
        log.info("新增系统配置，配置键：{}", config.getConfigKey());
        return rows;
    }

    @Override
    public int updateConfig(ProjectConfig config) {
        if (config.getId() == null) {
            throw new ServiceException("配置ID不能为空");
        }

        ProjectConfig existConfig = projectConfigMapper.selectById(config.getId());
        if (existConfig == null) {
            throw new ServiceException("配置不存在");
        }

        // 如果修改了配置键，需要校验唯一性
        if (!existConfig.getConfigKey().equals(config.getConfigKey())) {
            if (!checkConfigKeyUnique(config.getConfigKey())) {
                throw new ServiceException("配置键已存在");
            }
        }

        config.setUpdateBy(SecurityUtils.getUsername());
        config.setUpdateTime(new Date());

        int rows = projectConfigMapper.updateById(config);
        log.info("更新系统配置，配置键：{}", config.getConfigKey());
        return rows;
    }

    @Override
    public int deleteConfigByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            throw new ServiceException("请选择要删除的数据");
        }

        int rows = 0;
        for (Long id : ids) {
            rows += projectConfigMapper.deleteById(id);
        }

        log.info("批量删除系统配置，ID列表：{}", ids);
        return rows;
    }

    @Override
    public boolean checkConfigKeyUnique(String configKey) {
        ProjectConfig config = projectConfigMapper.selectByConfigKey(configKey);
        return config == null;
    }
}
