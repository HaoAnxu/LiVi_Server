package com.anxu.smarthomeunity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.anxu.smarthomeunity.mapper.ChatInfoMapper;
import com.anxu.smarthomeunity.mapper.WeCommunityMapper;
import com.anxu.smarthomeunity.model.dto.wecommunity.CommunityInfoDto;
import com.anxu.smarthomeunity.model.entity.wecommunity.ChatInfoEntity;
import com.anxu.smarthomeunity.model.entity.wecommunity.ChatInfoRelaEntity;
import com.anxu.smarthomeunity.model.entity.wecommunity.CommunityInfoEntity;
import com.anxu.smarthomeunity.model.entity.wecommunity.CommunityUserEntity;
import com.anxu.smarthomeunity.service.WeCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * WeCommunity相关服务实现类
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:04
 */
@Service
public class WeCommunityServiceImpl implements WeCommunityService {

    @Autowired
    private ChatInfoMapper chatInfoMapper;
    @Autowired
    private WeCommunityMapper weCommunityMapper;

    /**
     * 保存消息到数据库-返回消息ID
     *
     * @param chatInfoEntity 消息实体类Entity
     * @return msg_id
     */
    @Override
    public Long saveGroupMessage(ChatInfoEntity chatInfoEntity) {
        // 保存消息到数据库
        boolean result = chatInfoMapper.insert(chatInfoEntity) > 0;
        if (result) {
            Long msgId = chatInfoEntity.getMsgId();
            return msgId;
        }
        return null;
    }

    /**
     * 获取圈子所有成员ID
     *
     * @param communityId 圈子ID
     * @return 成员ID列表（无数据时返回空列表，避免null）
     */
    @Override
    public List<Integer> getCommunityAllMembers(Integer communityId) {
        return weCommunityMapper.selectUserIdsByCommunityId(communityId);
    }

    /**
     * 保存用户-消息关联信息到数据库
     *
     * @param chatInfoRelaEntity 关联实体类Entity
     */
    @Override
    public void saveUserMessageConnect(ChatInfoRelaEntity chatInfoRelaEntity) {
        weCommunityMapper.insert(chatInfoRelaEntity);
    }

    /**
     * 更新用户-消息关联信息-已读状态
     *
     * @param msgId  消息ID
     * @param userId 用户ID
     */
    @Override
    public void updateReadStatus(Long msgId, Integer userId) {
        weCommunityMapper.updateReadStatus(msgId, userId);
    }

    /**
     * 查询用户-消息关联信息-未读消息
     *
     * @param communityId 圈子ID
     * @param userId      用户ID
     * @return 未读消息列表（无数据时返回空列表，避免null）
     */
    @Override
    public List<ChatInfoEntity> getOfflineMessages(Integer communityId, Integer userId) {
        return weCommunityMapper.queryNoReadInfo(communityId, userId);
    }

    /**
     * 获取所有社区信息列表并且更新用户数
     *
     * @return 社区信息列表（无数据时返回空列表，避免null）
     */
    @Override
    public List<CommunityInfoDto> getAllCommunityList() {
        //更新所有圈子用户数
        weCommunityMapper.getAllCommunityList().forEach(
                communityInfoEntity -> {
                    weCommunityMapper.updateCommunityUserCount(communityInfoEntity.getCommunityId());
                }
        );
        //再查一遍
        List<CommunityInfoEntity> communityList = weCommunityMapper.getAllCommunityList();
        if (communityList == null) {
            return List.of();
        } else {
            return BeanUtil.copyToList(communityList, CommunityInfoDto.class);
        }
    }

    /**
     * 用户加入社区
     *
     * @param communityId 社区ID
     * @param userId      用户ID
     */
    @Override
    public void joinCommunity(Integer communityId, Integer userId) {
        // 保存用户-社区关联信息到数据库
        CommunityUserEntity communityUserEntity = new CommunityUserEntity();
        communityUserEntity.setCommunityId(communityId);
        communityUserEntity.setUserId(userId);
        communityUserEntity.setCreateTime(LocalDateTime.now());
        communityUserEntity.setUpdateTime(LocalDateTime.now());
        weCommunityMapper.joinCommunity(communityUserEntity);
    }

    /**
     * 用户退出社区
     *
     * @param communityId 社区ID
     * @param userId      用户ID
     */
    @Override
    public void exitCommunity(Integer communityId, Integer userId) {
        // 删除用户-社区关联信息
        weCommunityMapper.deleteByCommunityIdAndUserId(communityId, userId);
    }
}
