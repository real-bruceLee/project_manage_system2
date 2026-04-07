package com.ruoyi.project_manage.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.project_manage.domain.ProjectMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目成员Mapper接口
 *
 * @author ruoyi
 */
public interface ProjectMemberMapper extends BaseMapperPlus<ProjectMemberMapper, ProjectMember, ProjectMember> {

    /**
     * 批量插入项目成员
     *
     * @param members 成员列表
     * @return 结果
     */
    int batchInsert(@Param("list") List<ProjectMember> members);

    /**
     * 根据项目ID删除成员
     *
     * @param projectId 项目ID
     * @return 结果
     */
    int deleteByProjectId(@Param("projectId") Long projectId);

    /**
     * 根据项目ID查询成员列表
     *
     * @param projectId 项目ID
     * @return 成员列表
     */
    List<ProjectMember> selectByProjectId(@Param("projectId") Long projectId);
}
