package com.ruoyi.project_manage.domain.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 看板数据VO
 *
 * @author ruoyi
 */
@Data
public class DashboardVO {

    /** 项目总数 */
    private Integer totalProjects;

    /** 进行中项目数 */
    private Integer activeProjects;

    /** 已归档项目数 */
    private Integer archivedProjects;

    /** 延期项目数 */
    private Integer delayedProjects;

    /** 任务统计 */
    private TaskStatistics taskStatistics;

    /** 进度分布 */
    private List<Map<String, Object>> progressDistribution;

    /** 风险预警统计 */
    private List<Map<String, Object>> riskWarningStats;

    /** 近期任务列表 */
    private List<TaskOverviewVO> recentTasks;

    /**
     * 任务统计
     */
    @Data
    public static class TaskStatistics {
        /** 总任务数 */
        private Integer totalTasks;
        /** 未开始 */
        private Integer notStartTasks;
        /** 进行中 */
        private Integer inProgressTasks;
        /** 已完成 */
        private Integer completedTasks;
        /** 延期任务 */
        private Integer delayedTasks;
        /** 暂停任务 */
        private Integer pausedTasks;
    }
}
