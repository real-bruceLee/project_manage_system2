package com.ruoyi.project_manage.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.project_manage.domain.WbsDependency;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * WBS依赖关系Mapper接口
 *
 * @author ruoyi
 */
public interface WbsDependencyMapper extends BaseMapperPlus<WbsDependencyMapper, WbsDependency, WbsDependency> {

    /**
     * 根据任务ID查询前置依赖
     *
     * @param taskId 任务ID
     * @return 依赖列表
     */
    List<WbsDependency> selectByTaskId(@Param("taskId") Long taskId);

    /**
     * 根据前置任务ID查询依赖关系
     *
     * @param preTaskId 前置任务ID
     * @return 依赖列表
     */
    List<WbsDependency> selectByPreTaskId(@Param("preTaskId") Long preTaskId);

    /**
     * 批量插入依赖关系
     *
     * @param list 依赖列表
     * @return 结果
     */
    int batchInsert(@Param("list") List<WbsDependency> list);

    /**
     * 根据任务ID删除依赖关系
     *
     * @param taskId 任务ID
     * @return 结果
     */
    int deleteByTaskId(@Param("taskId") Long taskId);

    /**
     * 根据项目ID删除所有依赖关系
     *
     * @param projectId 项目ID
     * @return 结果
     */
    int deleteByProjectId(@Param("projectId") Long projectId);

    /**
     * 检查循环依赖
     *
     * @param taskId 当前任务ID
     * @param preTaskId 前置任务ID
     * @return 是否存在循环依赖
     */
    int checkCircularDependency(@Param("taskId") Long taskId, @Param("preTaskId") Long preTaskId);
}
