package com.ruoyi.project_manage.domain.query;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 变更申请查询对象
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChangeRequestQuery extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 项目ID */
    private Long projectId;

    /** 变更单号 */
    private String changeNo;

    /** 变更标题 */
    private String title;

    /** 变更类型 */
    private String changeType;

    /** 变更等级 */
    private Integer changeLevel;

    /** 审批状态 */
    private Integer status;

    /** 申请人ID */
    private Long applicantId;

    /** 申请时间范围 - 开始 */
    private Date applyTimeBegin;

    /** 申请时间范围 - 结束 */
    private Date applyTimeEnd;
}
