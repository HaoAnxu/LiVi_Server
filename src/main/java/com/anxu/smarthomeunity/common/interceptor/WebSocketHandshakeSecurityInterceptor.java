package com.anxu.smarthomeunity.common.interceptor;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSONObject;
import com.anxu.smarthomeunity.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * WebSocket握手安全拦截器
 * 用于验证WebSocket连接的握手请求，确保只有合法用户才能建立连接
 *
 * @Author: haoanxu
 * @Date: 2025/11/27 17:22
 */
@Component
@Slf4j
public class WebSocketHandshakeSecurityInterceptor implements HandshakeInterceptor {
    // 时间戳最大允许差值（5分钟）
    private static final long TIMESTAMP_MAX_DIFF = 5 * 60 * 1000;
    // 签名密钥（需和前端保持一致）
    private static final String SIGN_SECRET = "0611March7";

    //使用构造函数注入Jwt工具类
    private final JwtUtils jwtUtils;

    public WebSocketHandshakeSecurityInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * 握手前置拦截逻辑（核心方法）
     *
     * @param request    服务端HTTP请求对象
     * @param response   服务端HTTP响应对象
     * @param wsHandler  WebSocket处理器
     * @param attributes 握手属性容器（用于传递参数给WebSocket处理器）
     * @return true=允许握手，false=拒绝握手
     * @throws Exception 拦截过程中的异常
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 转换为Servlet请求对象，获取HTTP请求上下文
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpServletRequest req = servletRequest.getServletRequest();

        // ====================== 时间戳校验 ======================
        String timestamp = req.getParameter("timestamp");
        if (!validateTimestamp(timestamp)) {
            log.error("WS握手失败：时间戳无效");
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            writeErrorResponse(response, 400, "请求超时（时间戳无效）");
            return false;
        }

        // ====================== 签名校验 =====================
        String nonce = req.getParameter("nonce");
        String sign = req.getParameter("sign");
        if (!validateSign(timestamp, nonce, sign)) {
            log.error("WS握手失败：签名无效");
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            writeErrorResponse(response, 400, "签名无效（请求可能被篡改）");
            return false;
        }

        // ====================== Token校验 ======================
        String token = req.getParameter("token");
        if (!StringUtils.hasLength(token) || !jwtUtils.validateToken(token)) {
            log.error("WS握手失败：Token无效/过期");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            writeErrorResponse(response, 401, "未登录或Token过期");
            return false;
        }

        // ====================== 路径参数解析======================
        // 解析请求路径：/ws/community/{communityId}/{userId}
        String requestUri = req.getRequestURI();
        String[] pathParts = requestUri.split("/");
        if (pathParts.length < 5) { // 拆分后数组长度至少为5（["", "ws", "community", "1", "2"]）
            log.error("WS握手失败：路径格式错误，URI={}，拆分后长度={}", requestUri, pathParts.length);
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            writeErrorResponse(response, 400, "路径格式错误，正确格式：/ws/community/{communityId}/{userId}");
            return false;
        }

        // 解析社区ID和用户ID（路径参数）
        Integer communityId = null;
        Integer userId = null;
        try {
            communityId = Integer.parseInt(pathParts[3]);
            userId = Integer.parseInt(pathParts[4]);
        } catch (NumberFormatException e) {
            log.error("WS握手失败：路径参数解析失败，communityId={}, userId={}", pathParts[3], pathParts[4], e);
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            writeErrorResponse(response, 400, "社区ID/用户ID必须为数字");
            return false;
        }

        // ====================== Token用户ID和路径用户ID一致性校验 ======================
        Integer tokenUserId = jwtUtils.getUserId(token);
        if (!tokenUserId.equals(userId)) {
            log.error("WS握手失败：权限不足，Token用户ID={}，路径用户ID={}", tokenUserId, userId);
            response.setStatusCode(HttpStatus.FORBIDDEN);
            writeErrorResponse(response, 403, "无权限访问该社区");
            return false;
        }
        // ====================== 存入握手属性（供WebSocket处理器使用）======================
        // 将解析后的参数存入attributes，后续WeCommunityServer可通过session.getAttributes()获取
        attributes.put("communityId", communityId);
        attributes.put("userId", userId);
        // 临时注释：Token用户ID
        // attributes.put("tokenUserId", tokenUserId);

        log.info("WS握手校验通过：用户[{}] 社区[{}]", userId, communityId);
        return true; // 允许建立WebSocket连接
    }

    /**
     * 握手后置处理（无需业务逻辑）
     *
     * @param request   服务端HTTP请求对象
     * @param response  服务端HTTP响应对象
     * @param wsHandler WebSocket处理器
     * @param exception 握手过程中的异常（如有）
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 握手完成后无需处理，可留空或添加日志
        if (exception != null) {
            log.error("WS握手完成后出现异常", exception);
        }
    }

    /**
     * 校验时间戳是否有效（注释后暂不使用）
     * 校验规则：请求时间戳与当前时间差值不超过5分钟
     *
     * @param timestamp 前端传递的时间戳（毫秒级）
     * @return true=有效，false=无效
     */
    private boolean validateTimestamp(String timestamp) {
        if (!StringUtils.hasLength(timestamp)) return false;
        try {
            long requestTime = Long.parseLong(timestamp);
            long currentTime = System.currentTimeMillis();
            // 计算时间差绝对值，判断是否在允许范围内
            return Math.abs(currentTime - requestTime) <= TIMESTAMP_MAX_DIFF;
        } catch (NumberFormatException e) {
            log.error("时间戳格式错误，timestamp={}", timestamp, e);
            return false;
        }
    }

    /**
     * 校验签名是否有效（注释后暂不使用）
     * 签名规则：MD5(timestamp + nonce + SIGN_SECRET)
     *
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @param sign      前端传递的签名
     * @return true=有效，false=无效
     */
    private boolean validateSign(String timestamp, String nonce, String sign) {
        if (!StringUtils.hasLength(timestamp) || !StringUtils.hasLength(nonce) || !StringUtils.hasLength(sign)) {
            return false;
        }
        // 拼接签名源字符串
        String source = timestamp + nonce + SIGN_SECRET;
        // 生成服务端签名（MD5）
        String generatedSign = SecureUtil.md5(source);
        // 忽略大小写比较（兼容前端签名大小写问题）
        return generatedSign.equalsIgnoreCase(sign);
    }

    /**
     * 统一构建握手错误响应
     *
     * @param response 服务端HTTP响应对象
     * @param status   错误状态码
     * @param message  错误提示信息
     * @throws IOException 响应写入异常
     */
    private void writeErrorResponse(ServerHttpResponse response, int status, String message) throws IOException {
        // 设置响应Content-Type为JSON
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        // 构建错误响应JSON
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", status);
        jsonObject.put("msg", message);
        // 写入响应体
        try (PrintWriter writer = new PrintWriter(response.getBody())) {
            writer.write(jsonObject.toString());
            writer.flush();
        }
    }
}