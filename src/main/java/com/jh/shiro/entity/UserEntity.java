package com.jh.shiro.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 用户实体类
 *
 * @author jh
 * @date 2020/3/9
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity {
    /**
     * 唯一id
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
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
