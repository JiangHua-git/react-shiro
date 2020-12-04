/**
 * @title :  JwtUtils.java
 * @author: jh
 * @create: 2020/4/6 23:49
 * @modify:
 * @description : jwt工具类
 * @version: 1.0
 */
package com.jh.shiro.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @description：jwt工具类
 * @author: jh
 * @version: 1.0
 * @date: 2020/4/6 23:49
 */
@Slf4j
@Component
public class JwtUtils {
    /**
     * 签名的私钥
     */
    private static String key;

    /**
     * 失效时间
     */
    private static Long ttl;

    /**
     * @Value 注解只能给普通属性赋值，若要给静态变量赋值，可以使用set()方法，其中需要在类上加入@Component注解
     */
    @Value("${jwt.config.key}")
    public void setKey(String key) {
        JwtUtils.key = key;
    }

    @Value("${jwt.config.ttl}")
    public void setTtl(Long ttl) {
        JwtUtils.ttl = ttl;
    }

    /**
     * 设置认证token
     *
     * @param id   登录用户id
     * @param name 登录用户用户名
     * @param map  自定义数据
     * @return: String token
     */
    public static String createJwtToken(String id, String name, Map<String, Object> map) {
        long now = System.currentTimeMillis();
        long exp = now + ttl;
        log.info("ttl: ", ttl);
        //创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId("1").setSubject("admin")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key)
                //根据map设置claims
//                .setClaims(map)
                //设置失效时间（currentTimeMillis当前毫秒数）
                .setExpiration(new Date(exp));
        //创建token
        String token = jwtBuilder.compact();
        return token;
    }

    public static Claims parseJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
