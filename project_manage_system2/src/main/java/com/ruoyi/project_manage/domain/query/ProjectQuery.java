package com.ruoyi.project_manage.domain.query;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 项目信息查询对象
 *
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectQuery extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 项目名称 */
    private String name;

    /** 项目编号 */
    private String code;

    /** 项目负责人ID */
    private Long principalId;

    /** 项目状态 */
    private Integer status;

    /** 开始时间范围 - 开始 */
    private Date startTimeBegin;

    /** 开始时间范围 - 结束 */
    private Date startTimeEnd;

    /** 截止时间范围 - 开始 */
    private Date endTimeBegin;

    /** 截止时间范围 - 结束 */
    private Date endTimeEnd;

    /** 参数校验 */
    public boolean isTimeRangeValid() {
        if (startTimeBegin != null && startTimeEnd != null) {
            return !startTimeBegin.after(startTimeEnd);
        }
        if (endTimeBegin != null && endTimeEnd != null) {
            return !endTimeBegin.after(endTimeEnd);
        }
        return true;
    }
}
