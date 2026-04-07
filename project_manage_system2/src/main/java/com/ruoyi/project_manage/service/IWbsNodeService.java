package com.ruoyi.project_manage.service;

import com.ruoyi.project_manage.domain.dto.WbsNodeDTO;
import com.ruoyi.project_manage.domain.vo.WbsNodeVO;

import java.util.List;

/**
 * WBS节点Service接口
 *
 * @author ruoyi
 */
public interface IWbsNodeService {

    /**
     * 根据项目ID查询WBS树
     *
     * @param projectId 项目ID
     * @return WBS树
     */
    List<WbsNodeVO> selectWbsTreeByProjectId(Long projectId);

    /**
     * 根据ID查询WBS节点详情
     *
     * @param id 节点ID
     * @return WBS节点
     */
    WbsNodeVO selectWbsNodeById(Long id);

    /**
     * 新增/编辑WBS节点
     *
     * @param dto 节点信息
     * @return 结果
     */
    int saveWbsNode(WbsNodeDTO dto);

    /**
     * 删除WBS节点
     *
     * @param id 节点ID
     * @return 结果
     */
    int deleteWbsNode(Long id);

    /**
     * 批量删除WBS节点
     *
     * @param ids 节点ID数组
     * @return 结果
     */
    int deleteWbsNodes(Long[] ids);

    /**
     * 校验WBS编码是否唯一
     *
     * @param code WBS编码
     * @param projectId 项目ID
     * @return 结果
     */
    boolean checkCodeUnique(String code, Long projectId);

    /**
     * 拖拽调整节点位置
     *
     * @param id 节点ID
     * @param parentId 新的父节点ID
     * @param sortOrder 新的排序号
     * @return 结果
     */
    int moveNode(Long id, Long parentId, Integer sortOrder);

    /**
     * 检查循环依赖
     *
     * @param taskId 当前任务ID
     * @param preTaskId 前置任务ID
     * @return 是否存在循环依赖
     */
    boolean checkCircularDependency(Long taskId, Long preTaskId);

    /**
     * 校验子节点时间范围
     *
     * @param parentId 父节点ID
     * @param planStartTime 计划开始时间
     * @param planEndTime 计划结束时间
     * @return 结果
     */
    boolean validateChildTimeRange(Long parentId, Long excludeId);
}
