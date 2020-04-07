/**
 * @title :  JwtUtils.java
 * @author: jh
 * @create: 2020/4/6 23:49
 * @modify:
 * @description : jwt工具类
 * @version: 1.0
 */
package com.jh.shiro.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @description：jwt工具类
 * @author: jh
 * @version: 1.0
 * @date: 2020/4/6 23:49
 */
@Data
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
        //创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(name)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key)
                //根据map设置claims
                .setClaims(map)
                //设置失效时间（currentTimeMillis当前毫秒数）
                .setExpiration(new Date(exp));
        //创建token
        String token = jwtBuilder.compact();
        return token;
    }
}
