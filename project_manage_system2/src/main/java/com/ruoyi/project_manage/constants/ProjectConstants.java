package com.ruoyi.project_manage.constants;

/**
 * 项目管理模块常量类
 *
 * @author ruoyi
 */
public class ProjectConstants {

    /** 项目状态：进行中 */
    public static final Integer PROJECT_STATUS_ACTIVE = 0;

    /** 项目状态：已归档 */
    public static final Integer PROJECT_STATUS_ARCHIVED = 1;

    /** 项目状态：已作废 */
    public static final Integer PROJECT_STATUS_INVALID = 2;

    /** 默认项目进度 */
    public static final Integer DEFAULT_PROGRESS = 0;

    /** 最大进度值 */
    public static final Integer MAX_PROGRESS = 100;

    /** 删除标志：未删除 */
    public static final String DEL_FLAG_NORMAL = "0";

    /** 删除标志：已删除 */
    public static final String DEL_FLAG_DELETED = "1";

    /** 变更类型：计划变更 */
    public static final String CHANGE_TYPE_PLAN = "plan";

    /** 变更类型：进度变更 */
    public static final String CHANGE_TYPE_PROGRESS = "progress";

    /** 变更类型：其他变更 */
    public static final String CHANGE_TYPE_OTHER = "other";

    /** 消息类型：系统通知 */
    public static final String MESSAGE_TYPE_SYSTEM = "system";

    /** 消息类型：任务提醒 */
    public static final String MESSAGE_TYPE_TASK = "task";

    /** 消息类型：审批通知 */
    public static final String MESSAGE_TYPE_APPROVAL = "approval";

    /** 消息状态：未读 */
    public static final Integer MESSAGE_STATUS_UNREAD = 0;

    /** 消息状态：已读 */
    public static final Integer MESSAGE_STATUS_READ = 1;

    /** WBS根节点父ID */
    public static final Long WBS_ROOT_PARENT_ID = 0L;

    /** 默认预警阈值（天数） */
    public static final Integer DEFAULT_WARNING_DAYS = 7;
}
