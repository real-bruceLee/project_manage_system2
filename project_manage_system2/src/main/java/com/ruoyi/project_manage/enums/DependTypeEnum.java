package com.ruoyi.project_manage.enums;

import com.ruoyi.common.utils.StringUtils;

import java.io.Serializable;

/**
 * WBS节点依赖类型枚举
 * 对应字典：project_depend_type
 *
 * @author ruoyi
 */
public enum DependTypeEnum implements Serializable {

    /** 完成-开始 */
    FS("FS", "完成-开始"),

    /** 开始-开始 */
    SS("SS", "开始-开始"),

    /** 完成-完成 */
    FF("FF", "完成-完成"),

    /** 开始-完成 */
    SF("SF", "开始-完成");

    private final String code;
    private final String desc;

    DependTypeEnum(String code, String desc) {
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
    public static DependTypeEnum getByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (DependTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
