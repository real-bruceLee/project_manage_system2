package com.ruoyi.project_manage.service.impl;

import com.ruoyi.project_manage.domain.vo.DashboardVO;
import com.ruoyi.project_manage.domain.vo.TaskOverviewVO;
import com.ruoyi.project_manage.enums.TaskStatusEnum;
import com.ruoyi.project_manage.enums.WarningLevelEnum;
import com.ruoyi.project_manage.mapper.DashboardMapper;
import com.ruoyi.project_manage.service.IDashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 看板Service实现
 *
 * @author ruoyi
 */
@Slf4j
@Service
public class DashboardServiceImpl implements IDashboardService {

    @Autowired
    private DashboardMapper dashboardMapper;

    @Override
    public DashboardVO getDashboardData(Long projectId) {
        DashboardVO dashboardVO = new DashboardVO();

        // 项目统计
        Map<String, Object> projectStats = getProjectStatistics();
        dashboardVO.setTotalProjects((Integer) projectStats.get("total_projects"));
        dashboardVO.setActiveProjects((Integer) projectStats.get("active_projects"));
        dashboardVO.setArchivedProjects((Integer) projectStats.get("archived_projects"));
        dashboardVO.setDelayedProjects((Integer) projectStats.get("delayed_projects"));

        // 任务统计
        Map<String, Object> taskStats = getTaskStatistics(projectId);
        DashboardVO.TaskStatistics taskStatistics = new DashboardVO.TaskStatistics();
        taskStatistics.setTotalTasks((Integer) taskStats.get("total_tasks"));
        taskStatistics.setNotStartTasks((Integer) taskStats.get("not_start_tasks"));
        taskStatistics.setInProgressTasks((Integer) taskStats.get("in_progress_tasks"));
        taskStatistics.setCompletedTasks((Integer) taskStats.get("completed_tasks"));
        taskStatistics.setDelayedTasks((Integer) taskStats.get("delayed_tasks"));
        taskStatistics.setPausedTasks((Integer) taskStats.get("paused_tasks"));
        dashboardVO.setTaskStatistics(taskStatistics);

        // 进度分布
        dashboardVO.setProgressDistribution(getProgressDistribution(projectId));

        // 风险预警统计
        dashboardVO.setRiskWarningStats(getRiskWarningStats(projectId));

        // 近期任务
        List<TaskOverviewVO> recentTasks = getRecentTasks(projectId, 10);
        // 设置状态描述
        for (TaskOverviewVO task : recentTasks) {
            TaskStatusEnum statusEnum = TaskStatusEnum.getByCode(task.getStatus());
            if (statusEnum != null) {
                task.setStatusDesc(statusEnum.getDesc());
            }
        }
        dashboardVO.setRecentTasks(recentTasks);

        return dashboardVO;
    }

    @Override
    public Map<String, Object> getProjectStatistics() {
        return dashboardMapper.selectProjectStatistics();
    }

    @Override
    public Map<String, Object> getTaskStatistics(Long projectId) {
        return dashboardMapper.selectTaskStatistics(projectId);
    }

    @Override
    public List<Map<String, Object>> getProgressDistribution(Long projectId) {
        return dashboardMapper.selectProgressDistribution(projectId);
    }

    @Override
    public List<Map<String, Object>> getRiskWarningStats(Long projectId) {
        List<Map<String, Object>> stats = dashboardMapper.selectRiskWarningStats(projectId);
        // 添加预警等级描述
        for (Map<String, Object> stat : stats) {
            Integer level = (Integer) stat.get("warning_level");
            WarningLevelEnum levelEnum = WarningLevelEnum.getByCode(level);
            if (levelEnum != null) {
                stat.put("warning_level_desc", levelEnum.getDesc());
            }
        }
        return stats;
    }

    @Override
    public List<TaskOverviewVO> getRecentTasks(Long projectId, Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 10;
        }
        List<TaskOverviewVO> tasks = dashboardMapper.selectRecentTasks(projectId, limit);
        // 设置状态描述
        for (TaskOverviewVO task : tasks) {
            TaskStatusEnum statusEnum = TaskStatusEnum.getByCode(task.getStatus());
            if (statusEnum != null) {
                task.setStatusDesc(statusEnum.getDesc());
            }
        }
        return tasks;
    }

    @Override
    public List<TaskOverviewVO> getDelayedTasks(Long projectId, Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 10;
        }
        List<TaskOverviewVO> tasks = dashboardMapper.selectDelayedTasks(projectId, limit);
        // 设置状态描述
        for (TaskOverviewVO task : tasks) {
            TaskStatusEnum statusEnum = TaskStatusEnum.getByCode(task.getStatus());
            if (statusEnum != null) {
                task.setStatusDesc(statusEnum.getDesc());
            }
        }
        return tasks;
    }
}
