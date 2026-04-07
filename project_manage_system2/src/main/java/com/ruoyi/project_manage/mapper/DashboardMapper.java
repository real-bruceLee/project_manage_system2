package com.ruoyi.project_manage.mapper;

import com.ruoyi.project_manage.domain.vo.TaskOverviewVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 看板数据Mapper接口
 *
 * @author ruoyi
 */
public interface DashboardMapper {

    /**
     * 获取项目统计
     *
     * @return 项目统计
     */
    Map<String, Object> selectProjectStatistics();

    /**
     * 获取任务统计
     *
     * @param projectId 项目ID（可选）
     * @return 任务统计
     */
    Map<String, Object> selectTaskStatistics(@Param("projectId") Long projectId);

    /**
     * 获取进度分布
     *
     * @param projectId 项目ID（可选）
     * @return 进度分布
     */
    List<Map<String, Object>> selectProgressDistribution(@Param("projectId") Long projectId);

    /**
     * 获取风险预警统计
     *
     * @param projectId 项目ID（可选）
     * @return 风险预警统计
     */
    List<Map<String, Object>> selectRiskWarningStats(@Param("projectId") Long projectId);

    /**
     * 获取近期任务列表
     *
     * @param projectId 项目ID（可选）
     * @param limit 限制数量
     * @return 任务列表
     */
    List<TaskOverviewVO> selectRecentTasks(@Param("projectId") Long projectId, @Param("limit") Integer limit);

    /**
     * 获取延期任务列表
     *
     * @param projectId 项目ID（可选）
     * @param limit 限制数量
     * @return 任务列表
     */
    List<TaskOverviewVO> selectDelayedTasks(@Param("projectId") Long projectId, @Param("limit") Integer limit);
}
