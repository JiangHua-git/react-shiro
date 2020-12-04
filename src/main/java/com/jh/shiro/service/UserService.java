package com.jh.shiro.service;

import com.jh.shiro.entity.DataResult;
import com.jh.shiro.entity.UserEntity;

/**
 * 用户Service
 *
 * @author: jh
 * @date: 2020/3/10
 */
public interface UserService {
    /**
     * 获取所有用户信息
     *
     * @param
     * @return Result
     * @author jh
     * @date 2020/3/10
     */
    DataResult listUserInfo();

    /**
     * 获取用户信息
     *
     * @param name 姓名
     * @return Result
     * @author jh
     * @date 2020/3/10
     */
    DataResult getUserInfo(String name);

    /**
     * 获取用户权限
     *
     * @param name 用户名
     * @return Result
     * @author jh
     * @date 2020/3/11
     */
    DataResult getAuth(String name);

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return: UserEntity
     */
    UserEntity getUserInfoById(int id);
}
