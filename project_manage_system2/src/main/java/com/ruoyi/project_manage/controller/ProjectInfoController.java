package com.ruoyi.project_manage.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project_manage.domain.dto.ProjectDTO;
import com.ruoyi.project_manage.domain.query.ProjectQuery;
import com.ruoyi.project_manage.domain.vo.ProjectVO;
import com.ruoyi.project_manage.service.IProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 项目管理Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/project")
public class ProjectInfoController extends BaseController {

    @Autowired
    private IProjectInfoService projectInfoService;

    /**
     * 查询项目列表
     */
    @PreAuthorize("@ss.hasPermi('project_manage:project:list')")
    @GetMapping("/list")
    public TableDataInfo<ProjectVO> list(ProjectQuery query, PageQuery pageQuery) {
        return projectInfoService.selectProjectList(query, pageQuery);
    }

    /**
     * 导出项目列表
     */
    @PreAuthorize("@ss.hasPermi('project_manage:project:export')")
    @Log(title = "项目管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectQuery query) {
        // TODO: 实现导出功能
    }

    /**
     * 根据ID获取项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('project_manage:project:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(projectInfoService.selectProjectById(id));
    }

    /**
     * 新增项目
     */
    @PreAuthorize("@ss.hasPermi('project_manage:project:add')")
    @Log(title = "项目管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated ProjectDTO dto) {
        return toAjax(projectInfoService.insertProject(dto));
    }

    /**
     * 修改项目
     */
    @PreAuthorize("@ss.hasPermi('project_manage:project:edit')")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated ProjectDTO dto) {
        return toAjax(projectInfoService.updateProject(dto));
    }

    /**
     * 删除项目
     */
    @PreAuthorize("@ss.hasPermi('project_manage:project:del')")
    @Log(title = "项目管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(projectInfoService.deleteProjectByIds(ids));
    }

    /**
     * 校验项目编号是否唯一
     */
    @PreAuthorize("@ss.hasPermi('project_manage:project:add')")
    @GetMapping("/checkCodeUnique")
    public AjaxResult checkCodeUnique(@RequestParam String code) {
        return AjaxResult.success(projectInfoService.checkCodeUnique(code));
    }

    /**
     * 归档项目
     */
    @PreAuthorize("@ss.hasPermi('project_manage:project:edit')")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    @PutMapping("/archive/{id}")
    public AjaxResult archive(@PathVariable Long id) {
        return toAjax(projectInfoService.archiveProject(id));
    }

    /**
     * 作废项目
     */
    @PreAuthorize("@ss.hasPermi('project_manage:project:edit')")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    @PutMapping("/invalid/{id}")
    public AjaxResult invalid(@PathVariable Long id) {
        return toAjax(projectInfoService.invalidProject(id));
    }
}
