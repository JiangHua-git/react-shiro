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
     * @param id 用户id
     * @return String
     * @author jh
     * @date 2020/3/11
     */
    String getAuth(Integer id);

    /**
     * qq
     *
     * @param
     * @return
     */
    int updateUserInfo(UserEntity userEntity);
}
