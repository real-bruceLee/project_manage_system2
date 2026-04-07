package com.ruoyi.project_manage.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.project_manage.domain.vo.DashboardVO;
import com.ruoyi.project_manage.domain.vo.TaskOverviewVO;
import com.ruoyi.project_manage.service.IDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 看板Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController extends BaseController {

    @Autowired
    private IDashboardService dashboardService;

    /**
     * 获取看板数据
     */
    @PreAuthorize("@ss.hasPermi('project_manage:dashboard:list')")
    @GetMapping
    public AjaxResult getDashboardData(@RequestParam(required = false) Long projectId) {
        DashboardVO dashboardVO = dashboardService.getDashboardData(projectId);
        return AjaxResult.success(dashboardVO);
    }

    /**
     * 获取项目统计
     */
    @PreAuthorize("@ss.hasPermi('project_manage:dashboard:list')")
    @GetMapping("/projectStats")
    public AjaxResult getProjectStatistics() {
        Map<String, Object> stats = dashboardService.getProjectStatistics();
        return AjaxResult.success(stats);
    }

    /**
     * 获取任务统计
     */
    @PreAuthorize("@ss.hasPermi('project_manage:dashboard:list')")
    @GetMapping("/taskStats")
    public AjaxResult getTaskStatistics(@RequestParam(required = false) Long projectId) {
        Map<String, Object> stats = dashboardService.getTaskStatistics(projectId);
        return AjaxResult.success(stats);
    }

    /**
     * 获取进度分布
     */
    @PreAuthorize("@ss.hasPermi('project_manage:dashboard:list')")
    @GetMapping("/progressDistribution")
    public AjaxResult getProgressDistribution(@RequestParam(required = false) Long projectId) {
        List<Map<String, Object>> distribution = dashboardService.getProgressDistribution(projectId);
        return AjaxResult.success(distribution);
    }

    /**
     * 获取风险预警统计
     */
    @PreAuthorize("@ss.hasPermi('project_manage:dashboard:list')")
    @GetMapping("/riskWarningStats")
    public AjaxResult getRiskWarningStats(@RequestParam(required = false) Long projectId) {
        List<Map<String, Object>> stats = dashboardService.getRiskWarningStats(projectId);
        return AjaxResult.success(stats);
    }

    /**
     * 获取近期任务列表
     */
    @PreAuthorize("@ss.hasPermi('project_manage:dashboard:list')")
    @GetMapping("/recentTasks")
    public AjaxResult getRecentTasks(@RequestParam(required = false) Long projectId,
                                     @RequestParam(defaultValue = "10") Integer limit) {
        List<TaskOverviewVO> tasks = dashboardService.getRecentTasks(projectId, limit);
        return AjaxResult.success(tasks);
    }

    /**
     * 获取延期任务列表
     */
    @PreAuthorize("@ss.hasPermi('project_manage:dashboard:list')")
    @GetMapping("/delayedTasks")
    public AjaxResult getDelayedTasks(@RequestParam(required = false) Long projectId,
                                      @RequestParam(defaultValue = "10") Integer limit) {
        List<TaskOverviewVO> tasks = dashboardService.getDelayedTasks(projectId, limit);
        return AjaxResult.success(tasks);
    }
}
