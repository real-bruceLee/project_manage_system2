package com.ruoyi.project_manage.enums;

import com.ruoyi.common.utils.StringUtils;

import java.io.Serializable;

/**
 * WBS节点类型枚举
 * 对应字典：project_wbs_node_type
 *
 * @author ruoyi
 */
public enum WbsNodeTypeEnum implements Serializable {

    /** 阶段 */
    STAGE("stage", "阶段"),

    /** 里程碑 */
    MILESTONE("milestone", "里程碑"),

    /** 任务 */
    TASK("task", "任务"),

    /** 子任务 */
    SUB_TASK("sub_task", "子任务");

    private final String code;
    private final String desc;

    WbsNodeTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据code获取枚举
     */
    public static WbsNodeTypeEnum getByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (WbsNodeTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 判断是否为里程碑类型
     */
    public static boolean isMilestone(String code) {
        return MILESTONE.getCode().equals(code);
    }

    /**
     * 判断是否可以为父节点（里程碑不能作为父节点）
     */
    public static boolean canBeParent(String code) {
        return !MILESTONE.getCode().equals(code);
    }
}
