package com.ruoyi.project_manage.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.project_manage.constants.ProjectConstants;
import com.ruoyi.project_manage.domain.WbsDependency;
import com.ruoyi.project_manage.domain.WbsNode;
import com.ruoyi.project_manage.domain.dto.WbsDependencyDTO;
import com.ruoyi.project_manage.domain.dto.WbsNodeDTO;
import com.ruoyi.project_manage.domain.vo.WbsDependencyVO;
import com.ruoyi.project_manage.domain.vo.WbsNodeVO;
import com.ruoyi.project_manage.enums.WbsNodeTypeEnum;
import com.ruoyi.project_manage.mapper.WbsDependencyMapper;
import com.ruoyi.project_manage.mapper.WbsNodeMapper;
import com.ruoyi.project_manage.service.IWbsNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * WBS节点Service实现
 *
 * @author ruoyi
 */
@Slf4j
@Service
public class WbsNodeServiceImpl implements IWbsNodeService {

    @Autowired
    private WbsNodeMapper wbsNodeMapper;

    @Autowired
    private WbsDependencyMapper wbsDependencyMapper;

    @Override
    public List<WbsNodeVO> selectWbsTreeByProjectId(Long projectId) {
        List<WbsNode> allNodes = wbsNodeMapper.selectWbsTreeByProjectId(projectId);
        return buildTree(allNodes, 0L, 0);
    }

    @Override
    public WbsNodeVO selectWbsNodeById(Long id) {
        WbsNode node = wbsNodeMapper.selectWbsNodeById(id);
        if (node == null) {
            throw new ServiceException("WBS节点不存在");
        }

        WbsNodeVO vo = convertToVO(node);

        // 查询前置依赖
        List<WbsDependency> dependencies = wbsDependencyMapper.selectByTaskId(id);
        List<WbsDependencyVO> dependencyVOs = dependencies.stream().map(this::convertDependencyToVO).collect(Collectors.toList());
        vo.setPreTasks(dependencyVOs);

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveWbsNode(WbsNodeDTO dto) {
        // 校验WBS编码唯一性
        if (!checkCodeUnique(dto.getCode(), dto.getProjectId())) {
            throw new ServiceException("WBS编码已存在");
        }

        // 校验里程碑节点不能作为父节点
        if (dto.getParentId() != null && dto.getParentId() > 0) {
            WbsNode parentNode = wbsNodeMapper.selectWbsNodeById(dto.getParentId());
            if (parentNode != null && WbsNodeTypeEnum.isMilestone(parentNode.getType())) {
                throw new ServiceException("里程碑节点不能作为父节点");
            }
        }

        // 校验时间范围
        if (dto.getPlanStartTime() != null && dto.getPlanEndTime() != null) {
            if (dto.getPlanEndTime().before(dto.getPlanStartTime())) {
                throw new ServiceException("计划结束时间不能早于开始时间");
            }

            // 校验子节点时间是否在父节点范围内
            if (dto.getParentId() != null && dto.getParentId() > 0) {
                WbsNode parentNode = wbsNodeMapper.selectWbsNodeById(dto.getParentId());
                if (parentNode != null) {
                    if (dto.getPlanStartTime().before(parentNode.getPlanStartTime()) ||
                            dto.getPlanEndTime().after(parentNode.getPlanEndTime())) {
                        throw new ServiceException("子节点时间必须在父节点时间范围内");
                    }
                }
            }
        }

        WbsNode node = new WbsNode();
        BeanUtils.copyProperties(dto, node);

        int rows;
        if (dto.getId() == null) {
            // 新增
            node.setCreateBy(SecurityUtils.getUsername());
            node.setCreateTime(new Date());
            node.setDelFlag(ProjectConstants.DEL_FLAG_NORMAL);

            // 设置默认进度
            if (node.getProgress() == null) {
                node.setProgress(ProjectConstants.DEFAULT_PROGRESS);
            }

            rows = wbsNodeMapper.insert(node);
            dto.setId(node.getId());
            log.info("新增WBS节点，节点ID：{}，编码：{}", node.getId(), node.getCode());
        } else {
            // 编辑
            WbsNode existNode = wbsNodeMapper.selectWbsNodeById(dto.getId());
            if (existNode == null) {
                throw new ServiceException("WBS节点不存在");
            }

            node.setUpdateBy(SecurityUtils.getUsername());
            node.setUpdateTime(new Date());
            rows = wbsNodeMapper.updateById(node);

            // 删除旧的依赖关系
            wbsDependencyMapper.deleteByTaskId(dto.getId());

            log.info("更新WBS节点，节点ID：{}，编码：{}", dto.getId(), node.getCode());
        }

        // 保存前置依赖
        if (dto.getPreTasks() != null && !dto.getPreTasks().isEmpty()) {
            saveDependencies(dto.getId(), dto.getPreTasks());
        }

        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteWbsNode(Long id) {
        WbsNode node = wbsNodeMapper.selectWbsNodeById(id);
        if (node == null) {
            throw new ServiceException("WBS节点不存在");
        }

        // 检查是否有子节点
        List<WbsNode> children = wbsNodeMapper.selectByParentId(id);
        if (!children.isEmpty()) {
            throw new ServiceException("该节点存在子节点，请先删除子节点");
        }

        // 检查是否有关联的滚动计划或审批中的变更
        // TODO: 添加相关检查逻辑

        node.setDelFlag(ProjectConstants.DEL_FLAG_DELETED);
        node.setUpdateBy(SecurityUtils.getUsername());
        node.setUpdateTime(new Date());

        // 删除依赖关系
        wbsDependencyMapper.deleteByTaskId(id);

        log.info("删除WBS节点，节点ID：{}，编码：{}", id, node.getCode());
        return wbsNodeMapper.updateById(node);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteWbsNodes(Long[] ids) {
        if (ids == null || ids.length == 0) {
            throw new ServiceException("请选择要删除的数据");
        }

        int rows = 0;
        for (Long id : ids) {
            rows += deleteWbsNode(id);
        }

        return rows;
    }

    @Override
    public boolean checkCodeUnique(String code, Long projectId) {
        WbsNode node = wbsNodeMapper.checkCodeUnique(code, projectId);
        return node == null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int moveNode(Long id, Long parentId, Integer sortOrder) {
        WbsNode node = wbsNodeMapper.selectWbsNodeById(id);
        if (node == null) {
            throw new ServiceException("WBS节点不存在");
        }

        // 检查循环嵌套
        if (parentId != null && parentId > 0) {
            if (isCircularParent(id, parentId)) {
                throw new ServiceException("不能将父节点移动到其子节点下");
            }

            // 检查目标父节点是否为里程碑
            WbsNode parentNode = wbsNodeMapper.selectWbsNodeById(parentId);
            if (parentNode != null && WbsNodeTypeEnum.isMilestone(parentNode.getType())) {
                throw new ServiceException("不能将节点移动到里程碑节点下");
            }
        }

        // 更新父节点
        if (parentId != null) {
            wbsNodeMapper.updateParentId(id, parentId);
        }

        // 更新排序号
        if (sortOrder != null) {
            wbsNodeMapper.updateSortOrder(id, sortOrder);
        }

        log.info("移动WBS节点，节点ID：{}，新父节点：{}，新排序：{}", id, parentId, sortOrder);
        return 1;
    }

    @Override
    public boolean checkCircularDependency(Long taskId, Long preTaskId) {
        int count = wbsDependencyMapper.checkCircularDependency(taskId, preTaskId);
        return count > 0;
    }

    @Override
    public boolean validateChildTimeRange(Long parentId, Long excludeId) {
        WbsNode parentNode = wbsNodeMapper.selectWbsNodeById(parentId);
        if (parentNode == null) {
            return true;
        }

        List<WbsNode> children = wbsNodeMapper.selectByParentId(parentId);
        for (WbsNode child : children) {
            if (excludeId != null && excludeId.equals(child.getId())) {
                continue;
            }

            if (child.getPlanStartTime().before(parentNode.getPlanStartTime()) ||
                    child.getPlanEndTime().after(parentNode.getPlanEndTime())) {
                return false;
            }
        }

        return true;
    }

    /**
     * 构建树形结构
     */
    private List<WbsNodeVO> buildTree(List<WbsNode> allNodes, Long parentId, int level) {
        List<WbsNodeVO> result = new ArrayList<>();

        for (WbsNode node : allNodes) {
            Long nodeParentId = node.getParentId() == null ? 0L : node.getParentId();
            if (nodeParentId.equals(parentId)) {
                WbsNodeVO vo = convertToVO(node);
                vo.setLevel(level);
                vo.setChildren(buildTree(allNodes, node.getId(), level + 1));
                result.add(vo);
            }
        }

        return result;
    }

    /**
     * 检查是否循环嵌套
     */
    private boolean isCircularParent(Long nodeId, Long targetParentId) {
        if (nodeId.equals(targetParentId)) {
            return true;
        }

        List<WbsNode> children = wbsNodeMapper.selectByParentId(nodeId);
        for (WbsNode child : children) {
            if (isCircularParent(child.getId(), targetParentId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 保存依赖关系
     */
    private void saveDependencies(Long taskId, List<WbsDependencyDTO> dependencies) {
        List<WbsDependency> list = new ArrayList<>();
        String username = SecurityUtils.getUsername();

        for (WbsDependencyDTO dto : dependencies) {
            // 检查循环依赖
            if (checkCircularDependency(taskId, dto.getPreTaskId())) {
                throw new ServiceException("存在循环依赖，无法保存");
            }

            WbsDependency dependency = new WbsDependency();
            dependency.setTaskId(taskId);
            dependency.setPreTaskId(dto.getPreTaskId());
            dependency.setDependType(dto.getDependType());
            dependency.setDelayDays(dto.getDelayDays());
            dependency.setCreateBy(username);
            list.add(dependency);
        }

        if (!list.isEmpty()) {
            wbsDependencyMapper.batchInsert(list);
        }
    }

    /**
     * 转换为VO
     */
    private WbsNodeVO convertToVO(WbsNode node) {
        WbsNodeVO vo = new WbsNodeVO();
        BeanUtils.copyProperties(node, vo);
        return vo;
    }

    /**
     * 转换依赖关系到VO
     */
    private WbsDependencyVO convertDependencyToVO(WbsDependency dependency) {
        WbsDependencyVO vo = new WbsDependencyVO();
        BeanUtils.copyProperties(dependency, vo);

        // 查询前置任务信息
        WbsNode preTask = wbsNodeMapper.selectWbsNodeById(dependency.getPreTaskId());
        if (preTask != null) {
            vo.setPreTaskName(preTask.getName());
            vo.setPreTaskCode(preTask.getCode());
        }

        return vo;
    }
}
