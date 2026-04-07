package com.ruoyi.project_manage.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.project_manage.domain.ProjectConfig;
import com.ruoyi.project_manage.service.IProjectConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统配置Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/system")
public class ProjectConfigController extends BaseController {

    @Autowired
    private IProjectConfigService projectConfigService;

    /**
     * 查询配置列表
     */
    @PreAuthorize("@ss.hasPermi('project_manage:system:config')")
    @GetMapping("/config/list")
    public AjaxResult list() {
        List<ProjectConfig> list = projectConfigService.selectConfigList();
        return AjaxResult.success(list);
    }

    /**
     * 根据ID获取配置详情
     */
    @PreAuthorize("@ss.hasPermi('project_manage:system:config')")
    @GetMapping("/config/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(projectConfigService.selectConfigById(id));
    }

    /**
     * 根据配置键获取配置值
     */
    @PreAuthorize("@ss.hasPermi('project_manage:system:config')")
    @GetMapping("/config/value")
    public AjaxResult getConfigValue(@RequestParam String configKey) {
        return AjaxResult.success(projectConfigService.selectConfigValueByKey(configKey));
    }

    /**
     * 新增配置
     */
    @PreAuthorize("@ss.hasPermi('project_manage:system:config')")
    @Log(title = "系统配置", businessType = BusinessType.INSERT)
    @PostMapping("/config")
    public AjaxResult add(@RequestBody @Validated ProjectConfig config) {
        return toAjax(projectConfigService.insertConfig(config));
    }

    /**
     * 修改配置
     */
    @PreAuthorize("@ss.hasPermi('project_manage:system:config')")
    @Log(title = "系统配置", businessType = BusinessType.UPDATE)
    @PutMapping("/config")
    public AjaxResult edit(@RequestBody @Validated ProjectConfig config) {
        return toAjax(projectConfigService.updateConfig(config));
    }

    /**
     * 删除配置
     */
    @PreAuthorize("@ss.hasPermi('project_manage:system:config')")
    @Log(title = "系统配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/config/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(projectConfigService.deleteConfigByIds(ids));
    }

    /**
     * 校验配置键是否唯一
     */
    @PreAuthorize("@ss.hasPermi('project_manage:system:config')")
    @GetMapping("/config/checkConfigKeyUnique")
    public AjaxResult checkConfigKeyUnique(@RequestParam String configKey) {
        return AjaxResult.success(projectConfigService.checkConfigKeyUnique(configKey));
    }
}
