package com.ruoyi.project_manage.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.project_manage.domain.dto.ChangeRequestDTO;
import com.ruoyi.project_manage.domain.query.ChangeRequestQuery;
import com.ruoyi.project_manage.domain.vo.ChangeRequestVO;

/**
 * 变更申请Service接口
 *
 * @author ruoyi
 */
public interface IChangeRequestService {

    /**
     * 查询变更申请列表
     *
     * @param query 查询条件
     * @param pageQuery 分页参数
     * @return 变更申请列表
     */
    TableDataInfo<ChangeRequestVO> selectChangeRequestList(ChangeRequestQuery query, PageQuery pageQuery);

    /**
     * 根据ID查询变更申请详情
     *
     * @param id 变更ID
     * @return 变更申请
     */
    ChangeRequestVO selectChangeRequestById(Long id);

    /**
     * 新增变更申请
     *
     * @param dto 变更申请信息
     * @return 结果
     */
    int insertChangeRequest(ChangeRequestDTO dto);

    /**
     * 修改变更申请
     *
     * @param dto 变更申请信息
     * @return 结果
     */
    int updateChangeRequest(ChangeRequestDTO dto);

    /**
     * 删除变更申请
     *
     * @param ids 变更ID数组
     * @return 结果
     */
    int deleteChangeRequestByIds(Long[] ids);

    /**
     * 提交变更申请
     *
     * @param id 变更ID
     * @return 结果
     */
    int submitChangeRequest(Long id);

    /**
     * 审批通过变更申请
     *
     * @param id 变更ID
     * @param approveOpinion 审批意见
     * @return 结果
     */
    int approveChangeRequest(Long id, String approveOpinion);

    /**
     * 审批驳回变更申请
     *
     * @param id 变更ID
     * @param approveOpinion 审批意见
     * @return 结果
     */
    int rejectChangeRequest(Long id, String approveOpinion);

    /**
     * 撤回变更申请
     *
     * @param id 变更ID
     * @return 结果
     */
    int withdrawChangeRequest(Long id);

    /**
     * 生成变更单号
     *
     * @return 变更单号
     */
    String generateChangeNo();
}
