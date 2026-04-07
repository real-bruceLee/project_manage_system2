package com.ruoyi.project_manage.enums;

import com.ruoyi.common.utils.StringUtils;

import java.io.Serializable;

/**
 * 风险预警等级枚举
 * 对应字典：project_warning_level
 *
 * @author ruoyi
 */
public enum WarningLevelEnum implements Serializable {

    /** 一级预警（严重） */
    LEVEL1(1, "一级预警"),

    /** 二级预警（关注） */
    LEVEL2(2, "二级预警"),

    /** 三级预警（提醒） */
    LEVEL3(3, "三级预警");

    private final Integer code;
    private final String desc;

    WarningLevelEnum(Integer code, String desc) {
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
    public static WarningLevelEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (WarningLevelEnum level : values()) {
            if (level.getCode().equals(code)) {
                return level;
            }
        }
        return null;
    }

    /**
     * 根据desc获取枚举
     */
    public static WarningLevelEnum getByDesc(String desc) {
        if (StringUtils.isEmpty(desc)) {
            return null;
        }
        for (WarningLevelEnum level : values()) {
            if (level.getDesc().equals(desc)) {
                return level;
            }
        }
        return null;
    }
}
