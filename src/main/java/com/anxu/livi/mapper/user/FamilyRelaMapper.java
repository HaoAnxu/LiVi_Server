package com.anxu.livi.mapper.user;

import com.anxu.livi.model.entity.user.userFamilyRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * 用户家庭关系相关MP接口
 *
 * @Author: haoanxu
 * @Date: 2025/12/11 14:08
 */
@Mapper
public interface FamilyRelaMapper extends BaseMapper<userFamilyRelationEntity> {
}
