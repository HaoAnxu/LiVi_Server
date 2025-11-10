package com.anxu.smarthomeunity.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {
    //签名密钥
    @Value("${jwt.sign-key}")
    private String signKey;
    //过期时间
    private Long expire = 43200000L;

    /**
     * 生成JWT令牌
     * @param claims 自定义声明（键值对形式，如用户ID、角色等）
     * @return 生成的JWT令牌字符串
     */
    public String generateJwt(Map<String,Object> claims){
        String jwt = Jwts.builder()//创建JWT构建器（builder模式）
                .addClaims(claims)//添加自定义声明（payload中的核心数据）
                .signWith(SignatureAlgorithm.HS256,signKey)//使用HS256算法和签名密钥进行签名
                .setExpiration(new Date(System.currentTimeMillis()+expire))//设置过期时间
                .compact();//生成JWT字符串，格式为：header.payload.signature（三部分用.分隔）。
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt 待解析的JWT令牌字符串
     * @return 负载（payload）中存储的内容（包含标准声明和自定义声明）
     */
    public Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()//创建JWT解析器（builder模式）
                .setSigningKey(signKey)//设置签名密钥，用于验证令牌的签名是否匹配
                .parseClaimsJws(jwt)//解析JWT字符串，验证签名并提取负载（payload）中的声明
                .getBody();//获取负载（payload）中的声明（包含标准声明和自定义声明）
        return claims;
    }
}
