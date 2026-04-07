package com.ruoyi.project_manage.enums;

import com.ruoyi.common.utils.StringUtils;

import java.io.Serializable;

/**
 * 变更等级枚举
 * 对应字典：project_change_level
 *
 * @author ruoyi
 */
public enum ChangeLevelEnum implements Serializable {

    /** 一般变更 */
    GENERAL(1, "一般变更"),

    /** 重大变更 */
    MAJOR(2, "重大变更");

    private final Integer code;
    private final String desc;

    ChangeLevelEnum(Integer code, String desc) {
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
    public static ChangeLevelEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ChangeLevelEnum level : values()) {
            if (level.getCode().equals(code)) {
                return level;
            }
        }
        return null;
    }

    /**
     * 根据desc获取枚举
     */
    public static ChangeLevelEnum getByDesc(String desc) {
        if (StringUtils.isEmpty(desc)) {
            return null;
        }
        for (ChangeLevelEnum level : values()) {
            if (level.getDesc().equals(desc)) {
                return level;
            }
        }
        return null;
    }
}
