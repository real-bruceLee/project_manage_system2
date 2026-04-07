package com.ruoyi.project_manage.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.project_manage.domain.dto.ChangeRequestDTO;
import com.ruoyi.project_manage.domain.query.ChangeRequestQuery;
import com.ruoyi.project_manage.domain.vo.ChangeRequestVO;
import com.ruoyi.project_manage.service.IChangeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 变更管理Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/change")
public class ChangeRequestController extends BaseController {

    @Autowired
    private IChangeRequestService changeRequestService;

    /**
     * 查询变更申请列表
     */
    @PreAuthorize("@ss.hasPermi('project_manage:change:list')")
    @GetMapping("/list")
    public TableDataInfo<ChangeRequestVO> list(ChangeRequestQuery query, PageQuery pageQuery) {
        return changeRequestService.selectChangeRequestList(query, pageQuery);
    }

    /**
     * 根据ID获取变更申请详情
     */
    @PreAuthorize("@ss.hasPermi('project_manage:change:list')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(changeRequestService.selectChangeRequestById(id));
    }

    /**
     * 新增变更申请
     */
    @PreAuthorize("@ss.hasPermi('project_manage:change:add')")
    @Log(title = "变更管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated ChangeRequestDTO dto) {
        return toAjax(changeRequestService.insertChangeRequest(dto));
    }

    /**
     * 修改变更申请
     */
    @PreAuthorize("@ss.hasPermi('project_manage:change:edit')")
    @Log(title = "变更管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated ChangeRequestDTO dto) {
        return toAjax(changeRequestService.updateChangeRequest(dto));
    }

    /**
     * 删除变更申请
     */
    @PreAuthorize("@ss.hasPermi('project_manage:change:del')")
    @Log(title = "变更管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(changeRequestService.deleteChangeRequestByIds(ids));
    }

    /**
     * 提交变更申请
     */
    @PreAuthorize("@ss.hasPermi('project_manage:change:add')")
    @Log(title = "变更管理", businessType = BusinessType.UPDATE)
    @PutMapping("/submit/{id}")
    public AjaxResult submit(@PathVariable Long id) {
        return toAjax(changeRequestService.submitChangeRequest(id));
    }

    /**
     * 审批通过变更申请
     */
    @PreAuthorize("@ss.hasPermi('project_manage:change:approve')")
    @Log(title = "变更管理", businessType = BusinessType.UPDATE)
    @PutMapping("/approve/{id}")
    public AjaxResult approve(@PathVariable Long id, @RequestParam(required = false) String approveOpinion) {
        return toAjax(changeRequestService.approveChangeRequest(id, approveOpinion));
    }

    /**
     * 审批驳回变更申请
     */
    @PreAuthorize("@ss.hasPermi('project_manage:change:approve')")
    @Log(title = "变更管理", businessType = BusinessType.UPDATE)
    @PutMapping("/reject/{id}")
    public AjaxResult reject(@PathVariable Long id, @RequestParam(required = false) String approveOpinion) {
        return toAjax(changeRequestService.rejectChangeRequest(id, approveOpinion));
    }

    /**
     * 撤回变更申请
     */
    @PreAuthorize("@ss.hasPermi('project_manage:change:add')")
    @Log(title = "变更管理", businessType = BusinessType.UPDATE)
    @PutMapping("/withdraw/{id}")
    public AjaxResult withdraw(@PathVariable Long id) {
        return toAjax(changeRequestService.withdrawChangeRequest(id));
    }

    /**
     * 生成变更单号
     */
    @PreAuthorize("@ss.hasPermi('project_manage:change:add')")
    @GetMapping("/generateChangeNo")
    public AjaxResult generateChangeNo() {
        return AjaxResult.success(changeRequestService.generateChangeNo());
    }
}
