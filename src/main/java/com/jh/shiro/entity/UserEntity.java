package com.jh.shiro.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;

/**
 * 用户实体类
 *
 * @author jh
 * @date 2020/3/9
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity implements Serializable, AuthCachePrincipal,Comparable {
    public UserEntity() {
    }

    public UserEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 唯一id
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;

    @Override
    public String getAuthCacheKey() {
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 密码
     */
    private String password;
    /**
     * 页数
     */
    private Integer page;
    /**
     * 条数
     */
    private Integer size;
    /**
     * 权限
     */
    private String auth;
}
