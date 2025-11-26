package com.anxu.smarthomeunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.anxu.smarthomeunity.model.entity.wecommunity.ChatInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 聊天信息MP接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:01
 */
@Mapper
public interface ChatInfoMapper extends BaseMapper<ChatInfoEntity> {
}
