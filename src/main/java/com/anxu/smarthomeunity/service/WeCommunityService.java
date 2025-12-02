package com.anxu.smarthomeunity.service;

import com.anxu.smarthomeunity.model.dto.wecommunity.ChatHistoryQueryDTO;
import com.anxu.smarthomeunity.model.vo.wecommunity.ChatHistoryVO;
import com.anxu.smarthomeunity.model.vo.wecommunity.CommunityInfoVO;
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
    Integer saveGroupMessage(ChatInfoEntity chatInfoEntity);

    //获取圈子所有成员ID
    List<Integer> getCommunityAllMembers(Integer communityId);

    //插入消息-用户关联信息
    void saveUserMessageConnect(ChatInfoRelaEntity chatInfoRelaEntity);

    //更新阅读状态
    void updateReadStatus(Integer msgId, Integer userId);

    //查询用户在对应圈子里的未读消息
    List<ChatInfoEntity> getOfflineMessages(Integer circleId, Integer userId);

    //获取所有社区信息列表
    List<CommunityInfoVO> getAllCommunityList();

    //用户加入社区
    void joinCommunity(Integer communityId, Integer userId);

    //用户退出社区
    void exitCommunity(Integer communityId, Integer userId);

    //查询用户是否加入了该社区
    boolean isJoinCommunity(Integer communityId, Integer userId);

    //查询单个社区详情
    CommunityInfoVO getCommunityDetail(Integer communityId);

    //查询聊天记录
    ChatHistoryVO getChatHistory(ChatHistoryQueryDTO queryDTO);
}
