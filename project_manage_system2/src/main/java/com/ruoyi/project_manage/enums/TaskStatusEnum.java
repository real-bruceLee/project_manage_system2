package com.ruoyi.project_manage.enums;

import com.ruoyi.common.utils.StringUtils;

import java.io.Serializable;

/**
 * 任务状态枚举
 * 对应字典：project_task_status
 *
 * @author ruoyi
 */
public enum TaskStatusEnum implements Serializable {

    /** 未开始 */
    NOT_START(0, "未开始"),

    /** 逾期未开始 */
    OVERDUE_NOT_START(1, "逾期未开始"),

    /** 进行中 */
    IN_PROGRESS(2, "进行中"),

    /** 延期预警 */
    DELAY_WARNING(3, "延期预警"),

    /** 延期 */
    DELAY(4, "延期"),

    /** 已完成 */
    COMPLETED(5, "已完成"),

    /** 逾期完成 */
    OVERDUE_COMPLETED(6, "逾期完成"),

    /** 暂停 */
    PAUSED(7, "暂停");

    private final Integer code;
    private final String desc;

    TaskStatusEnum(Integer code, String desc) {
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
    public static TaskStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (TaskStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 根据desc获取枚举
     */
    public static TaskStatusEnum getByDesc(String desc) {
        if (StringUtils.isEmpty(desc)) {
            return null;
        }
        for (TaskStatusEnum status : values()) {
            if (status.getDesc().equals(desc)) {
                return status;
            }
        }
        return null;
    }
}
