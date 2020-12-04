/**
 * @title :  ShiroConfig.java
 * @author: jh
 * @create: 2020/5/11 23:07
 * @modify:
 * @description : shiro配置类
 * @version: 1.0
 */
package com.jh.shiro.config;

import com.jh.shiro.session.CustomSessionManager;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description：shiro配置类
 * @author: jh
 * @version: 1.0
 * @date: 2020/5/11 23:07
 */
@Configuration
public class ShiroConfig {

    /**
     * redis数据库地址
     */
    @Value("${spring.redis.host}")
    private String host;

    /**
     * redis数据库端口
     */
    @Value("${spring.redis.port}")
    private int port;

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置shiro内置过滤器
        /**
         * shiro过滤器，可以实现权限相关的拦截
         *      常用的过滤器
         *          anon：无需认证（登录）可以访问
         *          authc：必须认证才可以访问
         *          user：如果使用remeberMe功能才可以直接访问
         *          perms：该资源必须得到资源权限才可以访问
         *          role：该资源必须得到角色权限才可以访问
         *
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        //拦截add路径，切记加“/”，斜杠、斜杠、斜杠，重要的事情说三遍
        filterMap.put("/add", "perms[user:add]");
        /**
         * 授权过滤器
         * 注意，当前授权拦截后，shiro会跳转到未授权的页面
         */
        filterMap.put("/update", "perms[user:update]");

        //放行某个路径
        filterMap.put("/toLogin", "anon");

        filterMap.put("/login", "anon");

        //拦截所有(filterMap.put("/*","authc"))
        filterMap.put("/**", "anon");

        //修改登录页面
        shiroFilterFactoryBean.setLoginUrl("/autherror?code=1");

        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/autherror?code=2");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //关联realm
        securityManager.setRealm(realm);

        //将自定义的会话管理器注册到安全管理器中
        securityManager.setSessionManager(sessionManager());

        //将自定义的redis缓存管理器注册到安全管理器
        securityManager.setCacheManager(redisCacheManager());

        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }

    /**
     * 开启对shiro注解的支持
     *
     * @param securityManager 安全管理器
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 1.redis的控制器，操作redis
     */
    private RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        return redisManager;
    }

    /**
     * 2.sessionDao
     */
    private RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(this.redisManager());
        return redisSessionDAO;
    }

    /**
     * 3.会话管理器
     */
    private DefaultWebSessionManager sessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();
        customSessionManager.setSessionDAO(this.redisSessionDAO());
        return customSessionManager;
    }

    /**
     * 4.缓存管理器
     */
    private RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(this.redisManager());
        return redisCacheManager;
    }

}
