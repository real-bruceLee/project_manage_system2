package com.ruoyi.project_manage.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.project_manage.domain.dto.WbsNodeDTO;
import com.ruoyi.project_manage.domain.vo.WbsNodeVO;
import com.ruoyi.project_manage.service.IWbsNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WBS工作分解Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/wbs")
public class WbsNodeController extends BaseController {

    @Autowired
    private IWbsNodeService wbsNodeService;

    /**
     * 获取WBS树
     */
    @PreAuthorize("@ss.hasPermi('project_manage:wbs:list')")
    @GetMapping("/tree")
    public AjaxResult tree(@RequestParam Long projectId) {
        List<WbsNodeVO> list = wbsNodeService.selectWbsTreeByProjectId(projectId);
        return AjaxResult.success(list);
    }

    /**
     * 根据ID获取WBS节点详情
     */
    @PreAuthorize("@ss.hasPermi('project_manage:wbs:list')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(wbsNodeService.selectWbsNodeById(id));
    }

    /**
     * 新增/编辑WBS节点
     */
    @PreAuthorize("@ss.hasPermi('project_manage:wbs:save')")
    @Log(title = "WBS工作分解", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult save(@RequestBody @Validated WbsNodeDTO dto) {
        return toAjax(wbsNodeService.saveWbsNode(dto));
    }

    /**
     * 删除WBS节点
     */
    @PreAuthorize("@ss.hasPermi('project_manage:wbs:delete')")
    @Log(title = "WBS工作分解", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(wbsNodeService.deleteWbsNodes(ids));
    }

    /**
     * 校验WBS编码是否唯一
     */
    @PreAuthorize("@ss.hasPermi('project_manage:wbs:save')")
    @GetMapping("/checkCodeUnique")
    public AjaxResult checkCodeUnique(@RequestParam String code, @RequestParam Long projectId) {
        return AjaxResult.success(wbsNodeService.checkCodeUnique(code, projectId));
    }

    /**
     * 拖拽移动节点
     */
    @PreAuthorize("@ss.hasPermi('project_manage:wbs:save')")
    @Log(title = "WBS工作分解", businessType = BusinessType.UPDATE)
    @PutMapping("/move/{id}")
    public AjaxResult moveNode(@PathVariable Long id,
                               @RequestParam(required = false) Long parentId,
                               @RequestParam(required = false) Integer sortOrder) {
        return toAjax(wbsNodeService.moveNode(id, parentId, sortOrder));
    }

    /**
     * 检查循环依赖
     */
    @PreAuthorize("@ss.hasPermi('project_manage:wbs:save')")
    @GetMapping("/checkCircularDependency")
    public AjaxResult checkCircularDependency(@RequestParam Long taskId, @RequestParam Long preTaskId) {
        return AjaxResult.success(wbsNodeService.checkCircularDependency(taskId, preTaskId));
    }
}
