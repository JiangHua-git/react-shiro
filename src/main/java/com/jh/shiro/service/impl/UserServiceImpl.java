package com.jh.shiro.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jh.shiro.dao.UserDao;
import com.jh.shiro.entity.DataResult;
import com.jh.shiro.entity.UserEntity;
import com.jh.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户ServiceImpl
 *
 * @author jh
 * @date 2020/3/9
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 获取用户信息
     *
     * @return
     */
    @Override
    public DataResult listUserInfo() {
        List<UserEntity> list;
        DataResult dataResult = new DataResult();
        int page = 1;
        int size = 100;
        try {
            PageHelper.startPage(page, size);
            list = userDao.listUserInfo();
            PageInfo pageInfo = new PageInfo(list);
            dataResult.setMessage("查询成功");
            dataResult.setSuccess(true);
            dataResult.setTotalcount((int) pageInfo.getTotal());
            dataResult.setResult(list);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return dataResult;
    }

    /**
     * 获取用户信息
     *
     * @param name 姓名
     * @return com.jh.shiro.entity.Result 结果集
     * @author jh
     * @date 2020/3/11
     */
    @Override
    public DataResult getUserInfo(String name) {
        DataResult dataResult = new DataResult();
        UserEntity user;
        try {
            user = userDao.getUserInfo(name);
            dataResult.setMessage("查询成功");
            dataResult.setSuccess(true);
            dataResult.setTotalcount(1);
            dataResult.setData(user);
        } catch (Exception e) {
            log.error("查询错误: {}", e.getMessage());
        }
        return dataResult;
    }

    /**
     * 获取用户权限
     *
     * @param name 用户名
     * @return Result
     * @author jh
     * @date 2020/3/11
     */
    @Override
    public DataResult getAuth(String name) {
        List<String> authList;
        DataResult dataResult = new DataResult();
        try {
            authList = userDao.getAuth(name);
            dataResult.setSuccess(true);
            dataResult.setMessage("权限查询成功");
            dataResult.setResult(authList);
        } catch (Exception e) {
            log.error("查询错误: {}", e.getMessage());
        }
        return dataResult;
    }

    @Override
    public UserEntity getUserInfoById(int id) {
        UserEntity user = null;
        try {
            user = userDao.getUserInfoById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return user;
    }
}
