-- 项目计划与进度管理系统数据库脚本
-- 适用于MySQL数据库

-- ==============================
-- 项目信息表
-- ==============================
CREATE TABLE IF NOT EXISTS project_manage_info (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '项目ID',
    name VARCHAR(100) NOT NULL COMMENT '项目名称',
    code VARCHAR(50) NOT NULL COMMENT '项目编号',
    principal_id BIGINT(20) NOT NULL COMMENT '项目负责人ID',
    start_time DATE NOT NULL COMMENT '项目启动时间',
    end_time DATE NOT NULL COMMENT '项目截止时间',
    status TINYINT(1) DEFAULT 0 COMMENT '项目状态：0-进行中 1-已归档 2-已作废',
    total_progress INT(3) DEFAULT 0 COMMENT '整体进度（0-100）',
    description VARCHAR(500) DEFAULT NULL COMMENT '项目描述',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (id),
    UNIQUE KEY uk_project_code (code),
    KEY idx_project_status (status),
    KEY idx_project_principal (principal_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目信息表';

-- ==============================
-- 项目成员关联表
-- ==============================
CREATE TABLE IF NOT EXISTS project_manage_member (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    project_id BIGINT(20) NOT NULL COMMENT '项目ID',
    user_id BIGINT(20) NOT NULL COMMENT '成员用户ID',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (id),
    KEY idx_member_project (project_id),
    KEY idx_member_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目成员关联表';

-- ==============================
-- WBS工作分解表
-- ==============================
CREATE TABLE IF NOT EXISTS project_manage_wbs (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '节点ID',
    project_id BIGINT(20) NOT NULL COMMENT '项目ID',
    code VARCHAR(50) NOT NULL COMMENT 'WBS编码',
    name VARCHAR(100) NOT NULL COMMENT '节点名称',
    type VARCHAR(20) NOT NULL COMMENT '节点类型：stage-阶段、milestone-里程碑、task-任务、sub_task-子任务',
    parent_id BIGINT(20) DEFAULT 0 COMMENT '父节点ID',
    sort_order INT(10) DEFAULT 0 COMMENT '排序号',
    principal_id BIGINT(20) NOT NULL COMMENT '责任人ID',
    plan_start_time DATE DEFAULT NULL COMMENT '计划开始时间',
    plan_end_time DATE DEFAULT NULL COMMENT '计划结束时间',
    actual_start_time DATE DEFAULT NULL COMMENT '实际开始时间',
    actual_end_time DATE DEFAULT NULL COMMENT '实际结束时间',
    duration INT(5) DEFAULT NULL COMMENT '工期（天）',
    progress INT(3) DEFAULT 0 COMMENT '进度（0-100）',
    status TINYINT(1) DEFAULT 0 COMMENT '任务状态：0-未开始 1-逾期未开始 2-进行中 3-延期预警 4-延期 5-已完成 6-逾期完成 7-暂停',
    description VARCHAR(500) DEFAULT NULL COMMENT '任务描述',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (id),
    UNIQUE KEY uk_wbs_code_project (code, project_id),
    KEY idx_wbs_project (project_id),
    KEY idx_wbs_parent (parent_id),
    KEY idx_wbs_principal (principal_id),
    KEY idx_wbs_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='WBS工作分解表';

-- ==============================
-- WBS任务依赖关系表
-- ==============================
CREATE TABLE IF NOT EXISTS project_manage_wbs_dependency (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '依赖ID',
    task_id BIGINT(20) NOT NULL COMMENT '当前任务ID',
    pre_task_id BIGINT(20) NOT NULL COMMENT '前置任务ID',
    depend_type VARCHAR(10) DEFAULT 'FS' COMMENT '依赖类型：FS-完成-开始、SS-开始-开始、FF-完成-完成、SF-开始-完成',
    delay_days INT(3) DEFAULT 0 COMMENT '延隔天数',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (id),
    UNIQUE KEY uk_dependency_task_pre (task_id, pre_task_id),
    KEY idx_dependency_task (task_id),
    KEY idx_dependency_pre (pre_task_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='WBS任务依赖关系表';

-- ==============================
-- 三年滚动计划表
-- ==============================
CREATE TABLE IF NOT EXISTS project_manage_rolling_plan (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '计划ID',
    project_id BIGINT(20) NOT NULL COMMENT '项目ID',
    year INT(4) NOT NULL COMMENT '年份',
    quarter TINYINT(1) NOT NULL COMMENT '季度：1-4',
    wbs_id BIGINT(20) NOT NULL COMMENT 'WBS节点ID',
    plan_start_time DATE DEFAULT NULL COMMENT '计划开始时间',
    plan_end_time DATE DEFAULT NULL COMMENT '计划结束时间',
    plan_progress INT(3) DEFAULT 0 COMMENT '计划进度（0-100）',
    actual_progress INT(3) DEFAULT 0 COMMENT '实际进度（0-100）',
    status TINYINT(1) DEFAULT 0 COMMENT '状态：0-草稿 1-审批中 2-已通过 3-已驳回 4-已撤回',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (id),
    KEY idx_plan_project (project_id),
    KEY idx_plan_year (year),
    KEY idx_plan_wbs (wbs_id),
    KEY idx_plan_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='三年滚动计划表';

-- ==============================
-- 变更申请表
-- ==============================
CREATE TABLE IF NOT EXISTS project_manage_change (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '变更ID',
    project_id BIGINT(20) NOT NULL COMMENT '项目ID',
    change_no VARCHAR(50) NOT NULL COMMENT '变更单号',
    change_type VARCHAR(20) DEFAULT 'plan' COMMENT '变更类型：plan-计划变更、progress-进度变更、other-其他变更',
    change_level TINYINT(1) DEFAULT 1 COMMENT '变更等级：1-一般变更 2-重大变更',
    wbs_id BIGINT(20) DEFAULT NULL COMMENT '关联WBS节点ID',
    title VARCHAR(100) NOT NULL COMMENT '变更标题',
    content TEXT DEFAULT NULL COMMENT '变更内容',
    reason VARCHAR(500) DEFAULT NULL COMMENT '变更原因',
    impact VARCHAR(500) DEFAULT NULL COMMENT '影响分析',
    old_value VARCHAR(500) DEFAULT NULL COMMENT '原值',
    new_value VARCHAR(500) DEFAULT NULL COMMENT '新值',
    status TINYINT(1) DEFAULT 0 COMMENT '审批状态：0-草稿 1-审批中 2-已通过 3-已驳回 4-已撤回',
    applicant_id BIGINT(20) NOT NULL COMMENT '申请人ID',
    apply_time DATETIME DEFAULT NULL COMMENT '申请时间',
    approver_id BIGINT(20) DEFAULT NULL COMMENT '审批人ID',
    approve_time DATETIME DEFAULT NULL COMMENT '审批时间',
    approve_opinion VARCHAR(500) DEFAULT NULL COMMENT '审批意见',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (id),
    UNIQUE KEY uk_change_no (change_no),
    KEY idx_change_project (project_id),
    KEY idx_change_status (status),
    KEY idx_change_applicant (applicant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='变更申请表';

-- ==============================
-- 消息通知表
-- ==============================
CREATE TABLE IF NOT EXISTS project_manage_message (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    user_id BIGINT(20) NOT NULL COMMENT '接收用户ID',
    message_type VARCHAR(20) DEFAULT 'system' COMMENT '消息类型：system-系统通知、task-任务提醒、approval-审批通知',
    title VARCHAR(100) NOT NULL COMMENT '消息标题',
    content TEXT DEFAULT NULL COMMENT '消息内容',
    related_id BIGINT(20) DEFAULT NULL COMMENT '关联业务ID',
    related_type VARCHAR(50) DEFAULT NULL COMMENT '关联业务类型',
    is_read TINYINT(1) DEFAULT 0 COMMENT '是否已读：0-未读 1-已读',
    read_time DATETIME DEFAULT NULL COMMENT '阅读时间',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_message_user (user_id),
    KEY idx_message_read (is_read),
    KEY idx_message_type (message_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知表';

-- ==============================
-- 系统配置表
-- ==============================
CREATE TABLE IF NOT EXISTS project_manage_config (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    config_key VARCHAR(100) NOT NULL COMMENT '配置键',
    config_value VARCHAR(500) DEFAULT NULL COMMENT '配置值',
    config_desc VARCHAR(200) DEFAULT NULL COMMENT '配置说明',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- ==============================
-- 初始化配置数据
-- ==============================
INSERT INTO project_manage_config (config_key, config_value, config_desc) VALUES
('warning.days.level1', '3', '一级预警阈值（天）'),
('warning.days.level2', '7', '二级预警阈值（天）'),
('warning.days.level3', '14', '三级预警阈值（天）'),
('progress.update.interval', '7', '进度更新间隔（天）'),
('auto.reminder.enable', 'true', '是否启用自动提醒'),
('gantt.display.mode', 'day', '甘特图默认显示模式');
