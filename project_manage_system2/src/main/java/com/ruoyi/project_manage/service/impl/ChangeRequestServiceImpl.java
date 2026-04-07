package com.ruoyi.project_manage.service.impl;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.project_manage.constants.ProjectConstants;
import com.ruoyi.project_manage.domain.ChangeRequest;
import com.ruoyi.project_manage.domain.dto.ChangeRequestDTO;
import com.ruoyi.project_manage.enums.ApproveStatusEnum;
import com.ruoyi.project_manage.enums.ChangeLevelEnum;
import com.ruoyi.project_manage.domain.query.ChangeRequestQuery;
import com.ruoyi.project_manage.domain.vo.ChangeRequestVO;
import com.ruoyi.project_manage.mapper.ChangeRequestMapper;
import com.ruoyi.project_manage.service.IChangeRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 变更申请Service实现
 *
 * @author ruoyi
 */
@Slf4j
@Service
public class ChangeRequestServiceImpl implements IChangeRequestService {

    @Autowired
    private ChangeRequestMapper changeRequestMapper;

    @Override
    public TableDataInfo<ChangeRequestVO> selectChangeRequestList(ChangeRequestQuery query, PageQuery pageQuery) {
        List<ChangeRequest> list = changeRequestMapper.selectChangeRequestList(query);
        List<ChangeRequestVO> voList = list.stream().map(this::convertToVO).collect(Collectors.toList());
        return pageQuery.build(voList);
    }

    @Override
    public ChangeRequestVO selectChangeRequestById(Long id) {
        ChangeRequest changeRequest = changeRequestMapper.selectChangeRequestById(id);
        if (changeRequest == null) {
            throw new ServiceException("变更申请不存在");
        }
        return convertToVO(changeRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertChangeRequest(ChangeRequestDTO dto) {
        ChangeRequest changeRequest = new ChangeRequest();
        BeanUtils.copyProperties(dto, changeRequest);

        // 生成变更单号
        changeRequest.setChangeNo(generateChangeNo());

        // 设置初始状态为草稿
        changeRequest.setStatus(ApproveStatusEnum.DRAFT.getCode());
        changeRequest.setApplicantId(SecurityUtils.getUserId());
        changeRequest.setCreateBy(SecurityUtils.getUsername());
        changeRequest.setCreateTime(new Date());
        changeRequest.setDelFlag(ProjectConstants.DEL_FLAG_NORMAL);

        int rows = changeRequestMapper.insert(changeRequest);
        log.info("新增变更申请，变更ID：{}，变更单号：{}", changeRequest.getId(), changeRequest.getChangeNo());
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateChangeRequest(ChangeRequestDTO dto) {
        if (dto.getId() == null) {
            throw new ServiceException("变更ID不能为空");
        }

        ChangeRequest existRequest = changeRequestMapper.selectChangeRequestById(dto.getId());
        if (existRequest == null) {
            throw new ServiceException("变更申请不存在");
        }

        // 只有草稿状态可以编辑
        if (!ApproveStatusEnum.DRAFT.getCode().equals(existRequest.getStatus())) {
            throw new ServiceException("只有草稿状态的变更申请可以编辑");
        }

        ChangeRequest changeRequest = new ChangeRequest();
        BeanUtils.copyProperties(dto, changeRequest);
        changeRequest.setUpdateBy(SecurityUtils.getUsername());
        changeRequest.setUpdateTime(new Date());

        int rows = changeRequestMapper.updateById(changeRequest);
        log.info("更新变更申请，变更ID：{}，变更单号：{}", dto.getId(), existRequest.getChangeNo());
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteChangeRequestByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            throw new ServiceException("请选择要删除的数据");
        }

        int rows = 0;
        for (Long id : ids) {
            ChangeRequest changeRequest = changeRequestMapper.selectChangeRequestById(id);
            if (changeRequest != null) {
                // 只有草稿或已驳回状态可以删除
                if (!ApproveStatusEnum.DRAFT.getCode().equals(changeRequest.getStatus())
                        && !ApproveStatusEnum.REJECTED.getCode().equals(changeRequest.getStatus())) {
                    throw new ServiceException("只有草稿或已驳回状态的变更申请可以删除");
                }

                changeRequest.setDelFlag(ProjectConstants.DEL_FLAG_DELETED);
                changeRequest.setUpdateBy(SecurityUtils.getUsername());
                changeRequest.setUpdateTime(new Date());
                rows += changeRequestMapper.updateById(changeRequest);
            }
        }

        log.info("批量删除变更申请，ID列表：{}", ids);
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int submitChangeRequest(Long id) {
        ChangeRequest changeRequest = changeRequestMapper.selectChangeRequestById(id);
        if (changeRequest == null) {
            throw new ServiceException("变更申请不存在");
        }

        // 只有草稿状态可以提交
        if (!ApproveStatusEnum.DRAFT.getCode().equals(changeRequest.getStatus())) {
            throw new ServiceException("只有草稿状态的变更申请可以提交");
        }

        changeRequest.setStatus(ApproveStatusEnum.PENDING.getCode());
        changeRequest.setApplyTime(new Date());
        changeRequest.setUpdateBy(SecurityUtils.getUsername());
        changeRequest.setUpdateTime(new Date());

        log.info("提交变更申请，变更ID：{}，变更单号：{}", id, changeRequest.getChangeNo());
        return changeRequestMapper.updateById(changeRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int approveChangeRequest(Long id, String approveOpinion) {
        ChangeRequest changeRequest = changeRequestMapper.selectChangeRequestById(id);
        if (changeRequest == null) {
            throw new ServiceException("变更申请不存在");
        }

        // 只有审批中状态可以审批
        if (!ApproveStatusEnum.PENDING.getCode().equals(changeRequest.getStatus())) {
            throw new ServiceException("只有审批中状态的变更申请可以进行审批");
        }

        changeRequest.setStatus(ApproveStatusEnum.APPROVED.getCode());
        changeRequest.setApproverId(SecurityUtils.getUserId());
        changeRequest.setApproveTime(new Date());
        changeRequest.setApproveOpinion(approveOpinion);
        changeRequest.setUpdateBy(SecurityUtils.getUsername());
        changeRequest.setUpdateTime(new Date());

        // TODO: 根据变更内容更新WBS或项目信息

        log.info("审批通过变更申请，变更ID：{}，变更单号：{}", id, changeRequest.getChangeNo());
        return changeRequestMapper.updateById(changeRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int rejectChangeRequest(Long id, String approveOpinion) {
        ChangeRequest changeRequest = changeRequestMapper.selectChangeRequestById(id);
        if (changeRequest == null) {
            throw new ServiceException("变更申请不存在");
        }

        // 只有审批中状态可以审批
        if (!ApproveStatusEnum.PENDING.getCode().equals(changeRequest.getStatus())) {
            throw new ServiceException("只有审批中状态的变更申请可以进行审批");
        }

        changeRequest.setStatus(ApproveStatusEnum.REJECTED.getCode());
        changeRequest.setApproverId(SecurityUtils.getUserId());
        changeRequest.setApproveTime(new Date());
        changeRequest.setApproveOpinion(approveOpinion);
        changeRequest.setUpdateBy(SecurityUtils.getUsername());
        changeRequest.setUpdateTime(new Date());

        log.info("审批驳回变更申请，变更ID：{}，变更单号：{}", id, changeRequest.getChangeNo());
        return changeRequestMapper.updateById(changeRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int withdrawChangeRequest(Long id) {
        ChangeRequest changeRequest = changeRequestMapper.selectChangeRequestById(id);
        if (changeRequest == null) {
            throw new ServiceException("变更申请不存在");
        }

        // 只有审批中状态可以撤回
        if (!ApproveStatusEnum.PENDING.getCode().equals(changeRequest.getStatus())) {
            throw new ServiceException("只有审批中状态的变更申请可以撤回");
        }

        // 只能撤回自己提交的申请
        if (!SecurityUtils.getUserId().equals(changeRequest.getApplicantId())) {
            throw new ServiceException("只能撤回自己提交的变更申请");
        }

        changeRequest.setStatus(ApproveStatusEnum.WITHDRAWN.getCode());
        changeRequest.setUpdateBy(SecurityUtils.getUsername());
        changeRequest.setUpdateTime(new Date());

        log.info("撤回变更申请，变更ID：{}，变更单号：{}", id, changeRequest.getChangeNo());
        return changeRequestMapper.updateById(changeRequest);
    }

    @Override
    public String generateChangeNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        String randomStr = String.format("%04d", (int) (Math.random() * 10000));
        return "BG" + dateStr + randomStr;
    }

    /**
     * 转换为VO
     */
    private ChangeRequestVO convertToVO(ChangeRequest changeRequest) {
        ChangeRequestVO vo = new ChangeRequestVO();
        BeanUtils.copyProperties(changeRequest, vo);

        // 设置变更类型描述
        if (changeRequest.getChangeType() != null) {
            switch (changeRequest.getChangeType()) {
                case "plan":
                    vo.setChangeTypeDesc("计划变更");
                    break;
                case "progress":
                    vo.setChangeTypeDesc("进度变更");
                    break;
                case "other":
                    vo.setChangeTypeDesc("其他变更");
                    break;
                default:
                    vo.setChangeTypeDesc("未知");
            }
        }

        // 设置变更等级描述
        ChangeLevelEnum levelEnum = ChangeLevelEnum.getByCode(changeRequest.getChangeLevel());
        if (levelEnum != null) {
            vo.setChangeLevelDesc(levelEnum.getDesc());
        }

        // 设置审批状态描述
        ApproveStatusEnum statusEnum = ApproveStatusEnum.getByCode(changeRequest.getStatus());
        if (statusEnum != null) {
            vo.setStatusDesc(statusEnum.getDesc());
        }

        return vo;
    }
}
