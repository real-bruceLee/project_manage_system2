package com.ruoyi.project_manage.service;

import com.ruoyi.project_manage.domain.vo.DashboardVO;
import com.ruoyi.project_manage.domain.vo.TaskOverviewVO;

import java.util.List;
import java.util.Map;

/**
 * 看板Service接口
 *
 * @author ruoyi
 */
public interface IDashboardService {

    /**
     * 获取看板数据
     *
     * @param projectId 项目ID（可选，为空则查询全部）
     * @return 看板数据
     */
    DashboardVO getDashboardData(Long projectId);

    /**
     * 获取项目统计
     *
     * @return 项目统计
     */
    Map<String, Object> getProjectStatistics();

    /**
     * 获取任务统计
     *
     * @param projectId 项目ID（可选）
     * @return 任务统计
     */
    Map<String, Object> getTaskStatistics(Long projectId);

    /**
     * 获取进度分布
     *
     * @param projectId 项目ID（可选）
     * @return 进度分布
     */
    List<Map<String, Object>> getProgressDistribution(Long projectId);

    /**
     * 获取风险预警统计
     *
     * @param projectId 项目ID（可选）
     * @return 风险预警统计
     */
    List<Map<String, Object>> getRiskWarningStats(Long projectId);

    /**
     * 获取近期任务列表
     *
     * @param projectId 项目ID（可选）
     * @param limit 限制数量
     * @return 任务列表
     */
    List<TaskOverviewVO> getRecentTasks(Long projectId, Integer limit);

    /**
     * 获取延期任务列表
     *
     * @param projectId 项目ID（可选）
     * @param limit 限制数量
     * @return 任务列表
     */
    List<TaskOverviewVO> getDelayedTasks(Long projectId, Integer limit);
}
