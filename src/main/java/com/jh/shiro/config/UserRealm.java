package com.jh.shiro.config;

import com.jh.shiro.entity.DataResult;
import com.jh.shiro.entity.UserEntity;
import com.jh.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户
 *
 * @author: jh
 * @date: 2020/3/10
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Override
    public void setName(String name) {
        super.setName("userRealm");
    }

    @Resource
    private UserService userService;

    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @author jh
     * @date 2020/3/10
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("执行授权逻辑");
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //添加资源的授权字符串
        //info.addStringPermission("user:add");

        //获取当前用户
        UserEntity userEntity = (UserEntity) principalCollection.getPrimaryPrincipal();
        /*Subject subject = SecurityUtils.getSubject();
        UserEntity user = (UserEntity) subject.getPrincipal();*/
        String name = userEntity.getName();
        //到数据库查询当前用户权限
        DataResult dataResult;
        List<String> authList;
        try {
            dataResult = userService.getAuth(name);
            authList = (List<String>) dataResult.getResult();
            authList.forEach(System.out::println);
            info.addStringPermissions(authList);
        } catch (Exception e) {
            log.error("授权失败");
        }

        return info;
    }

    /**
     * 执行认证逻辑
     *
     * @param authenticationToken
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @author jh
     * @date 2020/3/10
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("执行认证逻辑");
        DataResult dataResult;
        UserEntity user;

        //1.判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String name = token.getUsername();
        dataResult = userService.getUserInfo(name);
        user = (UserEntity) dataResult.getData();
        if (user == null) {
            //用户名不存在，shiro会返回UnknowAccountException异常
            return null;
        }
        //2.判断密码
//        return new SimpleAuthenticationInfo(user, user.getPassword(), "userRealm");
        return null;
    }
}
