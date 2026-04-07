package com.ruoyi.project_manage.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.project_manage.domain.ChangeRequest;
import com.ruoyi.project_manage.domain.query.ChangeRequestQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 变更申请Mapper接口
 *
 * @author ruoyi
 */
public interface ChangeRequestMapper extends BaseMapperPlus<ChangeRequestMapper, ChangeRequest, ChangeRequest> {

    /**
     * 查询变更申请列表
     *
     * @param query 查询条件
     * @return 变更申请列表
     */
    List<ChangeRequest> selectChangeRequestList(@Param("query") ChangeRequestQuery query);

    /**
     * 根据ID查询变更申请详情
     *
     * @param id 变更ID
     * @return 变更申请
     */
    ChangeRequest selectChangeRequestById(@Param("id") Long id);

    /**
     * 校验变更单号是否唯一
     *
     * @param changeNo 变更单号
     * @return 变更申请
     */
    ChangeRequest checkChangeNoUnique(@Param("changeNo") String changeNo);
}
