package com.ruoyi.project_manage.service.impl;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.project_manage.constants.ProjectConstants;
import com.ruoyi.project_manage.domain.ProjectInfo;
import com.ruoyi.project_manage.domain.ProjectMember;
import com.ruoyi.project_manage.domain.dto.ProjectDTO;
import com.ruoyi.project_manage.domain.query.ProjectQuery;
import com.ruoyi.project_manage.domain.vo.ProjectMemberVO;
import com.ruoyi.project_manage.domain.vo.ProjectVO;
import com.ruoyi.project_manage.mapper.ProjectInfoMapper;
import com.ruoyi.project_manage.mapper.ProjectMemberMapper;
import com.ruoyi.project_manage.service.IProjectInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目信息Service实现
 *
 * @author ruoyi
 */
@Slf4j
@Service
public class ProjectInfoServiceImpl implements IProjectInfoService {

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Autowired
    private ProjectMemberMapper projectMemberMapper;

    @Override
    public TableDataInfo<ProjectVO> selectProjectList(ProjectQuery query, PageQuery pageQuery) {
        if (!query.isTimeRangeValid()) {
            throw new ServiceException("时间范围不合法，开始时间不能晚于结束时间");
        }
        List<ProjectInfo> list = projectInfoMapper.selectProjectList(query);
        List<ProjectVO> voList = list.stream().map(this::convertToVO).collect(Collectors.toList());
        return pageQuery.build(voList);
    }

    @Override
    public ProjectVO selectProjectById(Long id) {
        ProjectInfo project = projectInfoMapper.selectProjectById(id);
        if (project == null) {
            throw new ServiceException("项目不存在");
        }
        return convertToVO(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertProject(ProjectDTO dto) {
        // 校验项目编号唯一性
        if (!checkCodeUnique(dto.getCode())) {
            throw new ServiceException("项目编号已存在");
        }

        // 校验时间范围
        if (dto.getEndTime().before(dto.getStartTime())) {
            throw new ServiceException("项目截止时间不能早于启动时间");
        }

        ProjectInfo project = new ProjectInfo();
        BeanUtils.copyProperties(dto, project);
        project.setStatus(ProjectConstants.PROJECT_STATUS_ACTIVE);
        project.setTotalProgress(ProjectConstants.DEFAULT_PROGRESS);
        project.setCreateBy(SecurityUtils.getUsername());
        project.setCreateTime(new Date());
        project.setDelFlag(ProjectConstants.DEL_FLAG_NORMAL);

        int rows = projectInfoMapper.insert(project);

        // 保存项目成员
        if (dto.getMemberIds() != null && !dto.getMemberIds().isEmpty()) {
            saveProjectMembers(project.getId(), dto.getMemberIds());
        }

        log.info("新增项目成功，项目ID：{}，项目名称：{}", project.getId(), project.getName());
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateProject(ProjectDTO dto) {
        if (dto.getId() == null) {
            throw new ServiceException("项目ID不能为空");
        }

        ProjectInfo existProject = projectInfoMapper.selectProjectById(dto.getId());
        if (existProject == null) {
            throw new ServiceException("项目不存在");
        }

        // 已归档或已作废的项目不能编辑
        if (!ProjectConstants.PROJECT_STATUS_ACTIVE.equals(existProject.getStatus())) {
            throw new ServiceException("已归档或已作废的项目不能编辑");
        }

        // 校验时间范围
        if (dto.getEndTime().before(dto.getStartTime())) {
            throw new ServiceException("项目截止时间不能早于启动时间");
        }

        ProjectInfo project = new ProjectInfo();
        BeanUtils.copyProperties(dto, project);
        project.setUpdateBy(SecurityUtils.getUsername());
        project.setUpdateTime(new Date());

        int rows = projectInfoMapper.updateById(project);

        // 更新项目成员
        if (dto.getMemberIds() != null) {
            projectMemberMapper.deleteByProjectId(dto.getId());
            if (!dto.getMemberIds().isEmpty()) {
                saveProjectMembers(dto.getId(), dto.getMemberIds());
            }
        }

        log.info("更新项目成功，项目ID：{}，项目名称：{}", dto.getId(), dto.getName());
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteProjectByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            throw new ServiceException("请选择要删除的数据");
        }

        int rows = 0;
        for (Long id : ids) {
            ProjectInfo project = projectInfoMapper.selectProjectById(id);
            if (project != null) {
                project.setDelFlag(ProjectConstants.DEL_FLAG_DELETED);
                project.setUpdateBy(SecurityUtils.getUsername());
                project.setUpdateTime(new Date());
                rows += projectInfoMapper.updateById(project);
            }
        }

        log.info("批量删除项目，ID列表：{}", ids);
        return rows;
    }

    @Override
    public boolean checkCodeUnique(String code) {
        ProjectInfo project = projectInfoMapper.checkCodeUnique(code);
        return project == null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int archiveProject(Long id) {
        ProjectInfo project = projectInfoMapper.selectProjectById(id);
        if (project == null) {
            throw new ServiceException("项目不存在");
        }

        project.setStatus(ProjectConstants.PROJECT_STATUS_ARCHIVED);
        project.setUpdateBy(SecurityUtils.getUsername());
        project.setUpdateTime(new Date());

        log.info("归档项目，项目ID：{}", id);
        return projectInfoMapper.updateById(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int invalidProject(Long id) {
        ProjectInfo project = projectInfoMapper.selectProjectById(id);
        if (project == null) {
            throw new ServiceException("项目不存在");
        }

        project.setStatus(ProjectConstants.PROJECT_STATUS_INVALID);
        project.setUpdateBy(SecurityUtils.getUsername());
        project.setUpdateTime(new Date());

        log.info("作废项目，项目ID：{}", id);
        return projectInfoMapper.updateById(project);
    }

    @Override
    public int updateProjectProgress(Long projectId) {
        // TODO: 根据WBS任务进度计算项目整体进度
        return 0;
    }

    /**
     * 保存项目成员
     */
    private void saveProjectMembers(Long projectId, List<Long> memberIds) {
        List<ProjectMember> members = new ArrayList<>();
        String username = SecurityUtils.getUsername();
        for (Long userId : memberIds) {
            ProjectMember member = new ProjectMember();
            member.setProjectId(projectId);
            member.setUserId(userId);
            member.setCreateBy(username);
            members.add(member);
        }
        projectMemberMapper.batchInsert(members);
    }

    /**
     * 转换为VO
     */
    private ProjectVO convertToVO(ProjectInfo project) {
        ProjectVO vo = new ProjectVO();
        BeanUtils.copyProperties(project, vo);

        // 查询项目成员
        List<ProjectMember> members = projectMemberMapper.selectByProjectId(project.getId());
        List<ProjectMemberVO> memberVOs = members.stream().map(m -> {
            ProjectMemberVO memberVO = new ProjectMemberVO();
            BeanUtils.copyProperties(m, memberVO);
            return memberVO;
        }).collect(Collectors.toList());
        vo.setMembers(memberVOs);

        return vo;
    }
}
