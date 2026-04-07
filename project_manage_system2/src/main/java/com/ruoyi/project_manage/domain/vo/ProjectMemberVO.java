package com.ruoyi.project_manage.domain.vo;

import lombok.Data;

/**
 * 项目成员VO
 *
 * @author ruoyi
 */
@Data
public class ProjectMemberVO {

    /** 关联ID */
    private Long id;

    /** 项目ID */
    private Long projectId;

    /** 成员用户ID */
    private Long userId;

    /** 成员用户名 */
    private String userName;

    /** 成员昵称 */
    private String nickName;
}
