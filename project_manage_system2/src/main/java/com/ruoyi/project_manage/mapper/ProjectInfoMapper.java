package com.ruoyi.project_manage.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.project_manage.domain.ProjectInfo;
import com.ruoyi.project_manage.domain.query.ProjectQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目信息Mapper接口
 *
 * @author ruoyi
 */
public interface ProjectInfoMapper extends BaseMapperPlus<ProjectInfoMapper, ProjectInfo, ProjectInfo> {

    /**
     * 查询项目列表
     *
     * @param query 查询条件
     * @return 项目列表
     */
    List<ProjectInfo> selectProjectList(@Param("query") ProjectQuery query);

    /**
     * 根据ID查询项目详情
     *
     * @param id 项目ID
     * @return 项目信息
     */
    ProjectInfo selectProjectById(@Param("id") Long id);

    /**
     * 校验项目编号是否唯一
     *
     * @param code 项目编号
     * @return 项目信息
     */
    ProjectInfo checkCodeUnique(@Param("code") String code);

    /**
     * 更新项目进度
     *
     * @param projectId 项目ID
     * @param progress 进度值
     * @return 结果
     */
    int updateProjectProgress(@Param("projectId") Long projectId, @Param("progress") Integer progress);
}
