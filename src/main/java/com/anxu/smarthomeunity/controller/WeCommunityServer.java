package com.anxu.smarthomeunity.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.anxu.smarthomeunity.mapper.UserMapper;
import com.anxu.smarthomeunity.model.Result.WebSocketResult;
import com.anxu.smarthomeunity.model.dto.wecommunity.ChatHistoryQueryDTO;
import com.anxu.smarthomeunity.model.dto.wecommunity.ChatInfoDetailDTO;
import com.anxu.smarthomeunity.model.vo.wecommunity.ChatHistoryVO;
import com.anxu.smarthomeunity.model.entity.wecommunity.ChatInfoEntity;
import com.anxu.smarthomeunity.model.entity.wecommunity.ChatInfoRelaEntity;
import com.anxu.smarthomeunity.service.WeCommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WeCommunity—WebSocket接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:01
 */
@Component
@Slf4j
// 移除@ServerEndpoint注解，继承Spring的TextWebSocketHandler
public class WeCommunityServer extends TextWebSocketHandler {

    //全局静态唯一Map：存储所有连接实例（key: communityId:userId）
    private static final ConcurrentHashMap<String, WebSocketSession> online_instance_map = new ConcurrentHashMap<>();
    //全局静态唯一Map：存储每个社区的在线用户ID集合（key: communityId）
    private static final ConcurrentHashMap<Integer, Set<Integer>> community_online_userIds_map = new ConcurrentHashMap<>();

    @Autowired
    private WeCommunityService weCommunityService;
    @Autowired
    private UserMapper userMapper;

    /**
     * 连接建立成功时触发（替代原@OnOpen注解）
     * 从握手拦截器的属性中获取communityId和userId
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 从握手拦截器存入的属性中获取参数
        Integer communityId = (Integer) session.getAttributes().get("communityId");
        Integer userId = (Integer) session.getAttributes().get("userId");
        String uniqueKey = communityId + ":" + userId;

        log.info("连接建立 - communityId={}, userId={}, uniqueKey={}", communityId, userId, uniqueKey); // 【修改】补充日志验证ID

        // 加入在线用户Map，并且存储当前会话实例this
        online_instance_map.put(uniqueKey, session);
        // 先根据社区ID，查有没有对应的在线用户集合
        Set<Integer> onlineUserIds = community_online_userIds_map.get(communityId);
        //如果没有这个集合（说明这个社区第一次有人上线）
        if (onlineUserIds == null) {
            onlineUserIds = ConcurrentHashMap.newKeySet();
            // 把“社区ID-在线集合”存入 Map
            community_online_userIds_map.put(communityId, onlineUserIds);
        }
        // 把当前用户ID加入在线集合
        onlineUserIds.add(userId);

        log.info("用户[{}]连接社区[{}]成功！当前社区在线人数：{}",
                userId, communityId, community_online_userIds_map.get(communityId).size());

        // 上线后补推离线消息
        pushOfflineMessages(session, communityId, userId);
    }

    /**
     * 收到前端消息时触发（替代原@OnMessage注解）
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        String message = textMessage.getPayload();
        try {
            //处理心跳
            if("ping".equals(message)){
                sendMessage(session, "pong"); // 【修改】传递当前session参数
                return;
            }
            //获取消息类型
            JSONObject msgJson = JSONObject.parseObject(message);
            String msgType = msgJson.getString("msgType");
            //处理查询历史记录的类型
            if("request_chat_history".equals(msgType)){
                log.info("收到查询历史记录请求：{}", message);
                ChatHistoryQueryDTO queryDTO = new ChatHistoryQueryDTO();
                queryDTO.setCommunityId((Integer) session.getAttributes().get("communityId"));
                queryDTO.setLastMsgId((Integer) msgJson.get("lastMsgId"));
                if (msgJson.containsKey("pageSize")){
                    queryDTO.setPageSize(msgJson.getInteger("pageSize"));
                }
                ChatHistoryVO chatHistoryVO = weCommunityService.getChatHistory(queryDTO);
                WebSocketResult result = new WebSocketResult(
                        "chat_history_data",
                        JSON.toJSONString(chatHistoryVO)
                );
                sendMessage(session, JSON.toJSONString(result));
                return;
            }
            // 从握手拦截器存入的属性中获取参数
            Integer communityId = (Integer) session.getAttributes().get("communityId");
            Integer userId = (Integer) session.getAttributes().get("userId");
            log.info("收到社区[{}]用户[{}]的消息：{}", communityId, userId, message); // 【修改】补充日志验证ID
            //JSON转消息实体
            ChatInfoEntity chatInfoEntity = JSON.parseObject(message, ChatInfoEntity.class);
            //补全必要字段（从路径参数获取，前端不用传）
            chatInfoEntity.setCommunityId(communityId);    // 社区ID
            chatInfoEntity.setFromUserId(userId);    // 发送者ID

            //保存消息到数据库，获取自增msgId
            Integer msgId = weCommunityService.saveGroupMessage(chatInfoEntity);
            if (msgId == null) {
                log.error("消息发送失败：数据库存储失败");
                WebSocketResult webSocketResult = new WebSocketResult("error", "消息发送失败：服务器内部错误");
                sendMessage(session, JSON.toJSONString(webSocketResult)); //传递当前session参数
                return;
            }
            //把msgId回写到实体，供后面的方法用，无需定义外部变量
            chatInfoEntity.setMsgId(msgId);
            ChatInfoDetailDTO chatInfoDetailDTO = BeanUtil.copyProperties(chatInfoEntity, ChatInfoDetailDTO.class);
            chatInfoDetailDTO.setName(userMapper.selectById(userId).getUsername());
            chatInfoDetailDTO.setAvatar(userMapper.selectById(userId).getAvatar());
            //广播消息给社区所有成员（在线+离线）
            broadcastMessage(chatInfoDetailDTO, communityId);

        } catch (Exception e) {
            log.error("处理消息失败", e);
            WebSocketResult webSocketResult = new WebSocketResult("error", "消息发送失败：" + e.getMessage());
            sendMessage(session, JSON.toJSONString(webSocketResult));
        }
    }

    /**
     * 连接关闭时触发（替代原@OnClose注解）
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 从握手拦截器存入的属性中获取参数
        Integer communityId = (Integer) session.getAttributes().get("communityId");
        Integer userId = (Integer) session.getAttributes().get("userId");
        String uniqueKey = communityId + ":" + userId;

        log.info("用户[{}]断开社区[{}]连接", userId, communityId);

        // 1. 从在线用户Map移除
        online_instance_map.remove(uniqueKey);

        // 2. 从社区在线列表移除
        Set<Integer> onlineUserIds = community_online_userIds_map.get(communityId);
        if (onlineUserIds != null) {
            onlineUserIds.remove(userId);
            // 如果社区没人在线了，移除社区缓存
            if (onlineUserIds.isEmpty()) {
                community_online_userIds_map.remove(communityId);
                log.info("社区[{}]无在线用户，清除缓存", communityId);
            }
        }
    }

    /**
     * 连接出错时触发（替代原@OnError注解）
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        // 【修改】动态获取连接信息，替代原实例属性
        Integer communityId = (Integer) session.getAttributes().get("communityId");
        Integer userId = (Integer) session.getAttributes().get("userId");
        String uniqueKey = communityId + ":" + userId;

        log.error("用户[{}]社区[{}]连接异常", userId, communityId, throwable);

        // 出错时主动关闭连接
        if (session != null && session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                log.error("关闭异常连接失败", e);
            }
        }

        // 清理在线状态
        online_instance_map.remove(uniqueKey);
        Set<Integer> onlineUserIds = community_online_userIds_map.get(communityId);
        if (onlineUserIds != null) {
            onlineUserIds.remove(userId);
        }
    }

    /**
     * 广播消息给社区所有成员（在线+离线）
     */
    private void broadcastMessage(ChatInfoDetailDTO chatInfoDetailDTO, Integer communityId) { // 【修改】新增communityId参数
        try {
            String messageJson = JSON.toJSONString(chatInfoDetailDTO);
            WebSocketResult realtimeResult = new WebSocketResult("realtime", messageJson);
            String pushMessage = JSON.toJSONString(realtimeResult);
            // 1. 查社区所有成员（从用户-社区关联表 pub_community_and_user）
            List<Integer> allMemberIds = weCommunityService.getCommunityAllMembers(communityId);
            if (allMemberIds == null || allMemberIds.isEmpty()) {
                log.info("社区[{}]无成员，无需广播", communityId);
                return;
            }

            // 2. 获取当前社区在线用户列表
            Set<Integer> onlineUserIds = community_online_userIds_map.getOrDefault(communityId, ConcurrentHashMap.newKeySet());

            // 3. 遍历所有成员，区分在线/离线处理
            for (Integer memberId : allMemberIds) {
                if (onlineUserIds.contains(memberId)) {
                    // 在线成员：推送消息 + 创建已读关联记录
                    String onlineUniqueKey = communityId + ":" + memberId;
                    WebSocketSession targetSession = online_instance_map.get(onlineUniqueKey);
                    if (targetSession != null && targetSession.isOpen()) {
                        //发送带有type的json
                        targetSession.sendMessage(new TextMessage(pushMessage));
                        //数据库插入已读关联记录
                        ChatInfoRelaEntity connect = new ChatInfoRelaEntity();
                        connect.setUserId(memberId);
                        connect.setMsgId(chatInfoDetailDTO.getMsgId());
                        connect.setCommunityId(communityId);
                        connect.setReadStatus(1); // 已读
                        weCommunityService.saveUserMessageConnect(connect);
                        log.info("已给在线成员[{}]推送消息并创建已读记录", memberId);
                    } else {
                        // 异常：在线用户无连接，按离线处理
                        handleOfflineMember(chatInfoDetailDTO, memberId, communityId);
                    }
                } else {
                    // 离线成员：创建未读关联记录
                    handleOfflineMember(chatInfoDetailDTO, memberId, communityId);
                }
            }

        } catch (Exception e) {
            log.error("广播消息失败", e);
        }
    }

    /**
     * 处理离线成员：创建未读关联记录
     */
    private void handleOfflineMember(ChatInfoDetailDTO chatInfoDetailDTO, Integer memberId, Integer communityId) { // 【修改】新增communityId参数
        try {
            ChatInfoRelaEntity connect = new ChatInfoRelaEntity();
            connect.setUserId(memberId);
            connect.setMsgId(chatInfoDetailDTO.getMsgId());
            connect.setCommunityId(communityId); // 使用传入的communityId，替代原实例属性
            connect.setReadStatus(0); // 未读
            weCommunityService.saveUserMessageConnect(connect);
            log.info("已给离线成员[{}]创建未读关联记录", memberId);
        } catch (Exception e) {
            log.error("处理离线成员[{}]消息失败", memberId, e);
        }
    }

    /**
     * 补推离线消息（用户上线后）
     */
    private void pushOfflineMessages(WebSocketSession session, Integer communityId, Integer userId) { // 【修改】新增session、communityId、userId参数
        try {
            // 1. 查数据库：用户在该社区的未读消息
            List<ChatInfoDetailDTO> offlineMessages = weCommunityService.getOfflineMessages(communityId, userId);
            if (offlineMessages == null || offlineMessages.isEmpty()) {
                log.info("用户[{}]社区[{}]无离线消息", userId, communityId);
                return;
            }

            // 2. 遍历补推
            log.info("给用户[{}]补推离线消息{}条", userId, offlineMessages.size());
            for (ChatInfoDetailDTO offlineMsg : offlineMessages) {
                String messageJson = JSON.toJSONString(offlineMsg);
                WebSocketResult realtimeResult = new WebSocketResult("offline", messageJson);
                String pushMessage = JSON.toJSONString(realtimeResult);
                //发送带有type的json
                sendMessage(session, pushMessage);
                // 3. 补推成功，更新为已读
                weCommunityService.updateReadStatus(offlineMsg.getMsgId(), userId);
                log.info("已补推消息[{}]给用户[{}]并更新为已读", offlineMsg.getMsgId(), userId);
            }

        } catch (Exception e) {
            log.error("补推离线消息失败", e);
        }
    }

    /**
     * 给指定会话发送消息（适配Spring WebSocketSession）
     */
    private void sendMessage(WebSocketSession session, String message) {
        try {
            if (session != null && session.isOpen()) {
                // 替换原session.getBasicRemote().sendText为Spring的send方法
                session.sendMessage(new TextMessage(message));
            }
        } catch (IOException e) {
            // 动态获取userId，避免依赖实例属性
            Integer userId = session != null ? (Integer) session.getAttributes().get("userId") : null;
            log.error("给用户[{}]发消息失败：{}", userId, message, e);
        }
    }
}