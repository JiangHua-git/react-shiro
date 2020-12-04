package com.jh.shiro.dao;

import com.jh.shiro.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户Dao
 *
 * @author jh
 * @date 2020/3/9
 */
@Mapper
public interface UserDao {

    /**
     * 获取所有用户信息
     *
     * @return
     */
    List<UserEntity> listUserInfo();

    /**
     * 获取用户信息
     *
     * @param name 姓名
     * @return UserEntity
     * @author jh
     * @date 2020/3/11
     */
    UserEntity getUserInfo(String name);

    /**
     * 获取用户权限
     *
     * @param name 姓名
     * @return String
     * @author jh
     * @date 2020/3/11
     */
    List<String> getAuth(String name);

    /**
     * qq
     *
     * @param
     * @return
     */
    int updateUserInfo(UserEntity userEntity);

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return: UserEntity
     */
    UserEntity getUserInfoById(int id);
}
