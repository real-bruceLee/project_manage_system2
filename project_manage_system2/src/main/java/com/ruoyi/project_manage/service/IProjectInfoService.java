package com.ruoyi.project_manage.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.project_manage.domain.ProjectInfo;
import com.ruoyi.project_manage.domain.dto.ProjectDTO;
import com.ruoyi.project_manage.domain.query.ProjectQuery;
import com.ruoyi.project_manage.domain.vo.ProjectVO;

import java.util.List;

/**
 * 项目信息Service接口
 *
 * @author ruoyi
 */
public interface IProjectInfoService {

    /**
     * 查询项目列表
     *
     * @param query 查询条件
     * @param pageQuery 分页参数
     * @return 项目列表
     */
    TableDataInfo<ProjectVO> selectProjectList(ProjectQuery query, PageQuery pageQuery);

    /**
     * 根据ID查询项目详情
     *
     * @param id 项目ID
     * @return 项目信息
     */
    ProjectVO selectProjectById(Long id);

    /**
     * 新增项目
     *
     * @param dto 项目信息
     * @return 结果
     */
    int insertProject(ProjectDTO dto);

    /**
     * 修改项目
     *
     * @param dto 项目信息
     * @return 结果
     */
    int updateProject(ProjectDTO dto);

    /**
     * 删除项目
     *
     * @param ids 项目ID数组
     * @return 结果
     */
    int deleteProjectByIds(Long[] ids);

    /**
     * 校验项目编号是否唯一
     *
     * @param code 项目编号
     * @return 结果
     */
    boolean checkCodeUnique(String code);

    /**
     * 归档项目
     *
     * @param id 项目ID
     * @return 结果
     */
    int archiveProject(Long id);

    /**
     * 作废项目
     *
     * @param id 项目ID
     * @return 结果
     */
    int invalidProject(Long id);

    /**
     * 更新项目进度
     *
     * @param projectId 项目ID
     * @return 结果
     */
    int updateProjectProgress(Long projectId);
}
