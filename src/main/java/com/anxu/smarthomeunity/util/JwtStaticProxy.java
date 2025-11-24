package com.anxu.smarthomeunity.util;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
/**
 * JWT 工具类静态代理
 *
 * @Author: haoanxu
 * @Date: 2025/11/20 17:01
 */
@Component
public class JwtStaticProxy {
    // 注入JwtUtils实例（由Spring容器管理）
    private static JwtUtils jwtUtils;

    // 构造器注入（确保静态变量被赋值）
    @Autowired
    public JwtStaticProxy(JwtUtils jwtUtils) {
        JwtStaticProxy.jwtUtils = jwtUtils;
    }

    /**
     * 静态代理：生成JWT令牌
     */
    public static String generateJwt(Integer id,String username) {
        return jwtUtils.generateJwt(id,username);
    }

    /**
     * 静态代理：校验JWT令牌
     */
    public static boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }


    /**
     * 静态代理：获取用户ID
     */
    public static Integer getUserId(String token) {
        return jwtUtils.getUserId(token);
    }

    /**
     * 静态代理：获取用户名
     */
    public static String getUsername(String token) {
        return jwtUtils.getUsername(token);
    }

}
