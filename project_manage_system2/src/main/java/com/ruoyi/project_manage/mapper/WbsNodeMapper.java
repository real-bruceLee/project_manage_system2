package com.ruoyi.project_manage.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.project_manage.domain.WbsNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * WBS节点Mapper接口
 *
 * @author ruoyi
 */
public interface WbsNodeMapper extends BaseMapperPlus<WbsNodeMapper, WbsNode, WbsNode> {

    /**
     * 根据项目ID查询WBS树
     *
     * @param projectId 项目ID
     * @return WBS节点列表
     */
    List<WbsNode> selectWbsTreeByProjectId(@Param("projectId") Long projectId);

    /**
     * 根据ID查询WBS节点详情
     *
     * @param id 节点ID
     * @return WBS节点
     */
    WbsNode selectWbsNodeById(@Param("id") Long id);

    /**
     * 校验WBS编码是否唯一
     *
     * @param code WBS编码
     * @param projectId 项目ID
     * @return WBS节点
     */
    WbsNode checkCodeUnique(@Param("code") String code, @Param("projectId") Long projectId);

    /**
     * 根据父节点ID查询子节点
     *
     * @param parentId 父节点ID
     * @return 子节点列表
     */
    List<WbsNode> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 根据项目ID删除所有节点
     *
     * @param projectId 项目ID
     * @return 结果
     */
    int deleteByProjectId(@Param("projectId") Long projectId);

    /**
     * 更新节点排序号
     *
     * @param id 节点ID
     * @param sortOrder 排序号
     * @return 结果
     */
    int updateSortOrder(@Param("id") Long id, @Param("sortOrder") Integer sortOrder);

    /**
     * 更新节点父ID
     *
     * @param id 节点ID
     * @param parentId 父节点ID
     * @return 结果
     */
    int updateParentId(@Param("id") Long id, @Param("parentId") Long parentId);
}
