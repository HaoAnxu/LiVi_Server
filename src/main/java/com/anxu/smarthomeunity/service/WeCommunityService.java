package com.anxu.smarthomeunity.service;

import com.anxu.smarthomeunity.model.dto.wecommunity.CommunityInfoDto;
import com.anxu.smarthomeunity.model.entity.wecommunity.ChatInfoEntity;
import com.anxu.smarthomeunity.model.entity.wecommunity.ChatInfoRelaEntity;

import java.util.List;

/**
 * WeCommunity相关服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:03
 */
public interface WeCommunityService {
    //保存消息到数据库-返回msg_id
    Long saveGroupMessage(ChatInfoEntity chatInfoEntity);

    //获取圈子所有成员ID
    List<Integer> getCommunityAllMembers(Integer communityId);

    //插入消息-用户关联信息
    void saveUserMessageConnect(ChatInfoRelaEntity chatInfoRelaEntity);

    //更新阅读状态
    void updateReadStatus(Long msgId, Integer userId);

    //查询用户在对应圈子里的未读消息
    List<ChatInfoEntity> getOfflineMessages(Integer circleId, Integer userId);

    //获取所有社区信息列表
    List<CommunityInfoDto> getAllCommunityList();

    //用户加入社区
    void joinCommunity(Integer communityId, Integer userId);

    //用户退出社区
    void exitCommunity(Integer communityId, Integer userId);
}
