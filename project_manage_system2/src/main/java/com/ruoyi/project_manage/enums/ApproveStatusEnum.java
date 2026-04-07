package com.ruoyi.project_manage.enums;

import com.ruoyi.common.utils.StringUtils;

import java.io.Serializable;

/**
 * 审批状态枚举
 * 对应字典：project_approve_status
 *
 * @author ruoyi
 */
public enum ApproveStatusEnum implements Serializable {

    /** 草稿 */
    DRAFT(0, "草稿"),

    /** 审批中 */
    PENDING(1, "审批中"),

    /** 已通过 */
    APPROVED(2, "已通过"),

    /** 已驳回 */
    REJECTED(3, "已驳回"),

    /** 已撤回 */
    WITHDRAWN(4, "已撤回");

    private final Integer code;
    private final String desc;

    ApproveStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据code获取枚举
     */
    public static ApproveStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ApproveStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 根据desc获取枚举
     */
    public static ApproveStatusEnum getByDesc(String desc) {
        if (StringUtils.isEmpty(desc)) {
            return null;
        }
        for (ApproveStatusEnum status : values()) {
            if (status.getDesc().equals(desc)) {
                return status;
            }
        }
        return null;
    }
}
