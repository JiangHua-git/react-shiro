package com.jh.shiro.controller;

import com.alibaba.fastjson.JSON;
import com.jh.shiro.entity.DataResult;
import com.jh.shiro.entity.UserEntity;
import com.jh.shiro.service.UserService;
import com.jh.shiro.util.JwtUtils;
import com.jh.shiro.util.Result;
import com.jh.shiro.util.ResultCode;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户Controller
 * Thymeleaf只能用@Controller，切记
 *
 * @author: jh
 * @date: 2020/3/10
 */
@Slf4j
@CrossOrigin
@RestController
public class UserController {
    @Resource(name = "userServiceImpl")
    private UserService userService;

    /**
     * 获取用户信息
     *
     * @param
     * @return com.jh.shiro.entity.Result
     * @author jh
     * @date 2020/3/10
     */
    @PostMapping(value = {"shiro/listUserInfo"})
    public DataResult listUserInfo() {
        DataResult dataResult;
        try {
            dataResult = userService.listUserInfo();
        } catch (Exception e) {
            dataResult = new DataResult();
            dataResult.setMessage("查询失败");
            dataResult.setSuccess(false);
            log.error(e.getMessage());
        }
        return dataResult;
    }

    /**
     * 测试thymeleaf
     *
     * @param
     * @return java.lang.String
     * @author jh
     * @date 2020/3/10
     */
    @GetMapping(value = "hello")
    public String thymeleafTest(Model model) {
        model.addAttribute("name", "劣者姜某");
        return "test";
    }

    @GetMapping(value = "toLogin")
    public String aaa() {
        return "login";
    }

    @GetMapping("add")
    public String add() {
        return "user/add";
    }

    @GetMapping("update")
    public String update() {
        return "user/update";
    }


    /**
     * 登录
     *
     * @param
     * @return java.lang.String
     * @author jh
     * @date 2020/3/11
     */
   @RequestMapping("login")
    public Result login(@RequestBody UserEntity userEntity, Model model) {
        Result result;
        StringBuilder sb = new StringBuilder("用户名: ");
        String name = userEntity.getName();
        String password = userEntity.getPassword();

        log.info(sb.append(name).append("  密码:").append(password).toString());
        /**
         * 使用shiro编写认证操心
         */
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();

        //2.获取sessionId
        String sessionId = (String) subject.getSession().getId();
        log.info("MySessionId: {}", sessionId);

        /**
         * 3.密码加密
         *      Md5Hash:
         *      参数一：加密的内容
         *      参数二：盐（加密的混淆字符串）
         *      参数三：加密次数
         */
        String newPassword = new Md5Hash(password, name, 3).toString();

        //4.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, newPassword);

        //5.执行登录方法
        try {
            subject.login(token);
            model.addAttribute("msg", "登录成功");
            log.info("登录成功");
            result = new Result(ResultCode.LOGINSUCCESS);
            token.setHost(InetAddress.getLocalHost().toString());
            token.setUsername(sessionId);
            result.setToken(token);
            result.setData(sessionId);
        } catch (UnknownHostException e) {
            result = new Result(ResultCode.FAIL);
            result.setMessage("获取主机地址失败");
            log.info("获取主机地址失败");
        } catch (UnknownAccountException e) {
            result = new Result(ResultCode.FAIL);
            result.setMessage("用户名不存在");
            log.info("用户名不存在");
        } catch (IncorrectCredentialsException e) {
            result = new Result(ResultCode.FAIL);
            result.setMessage("密码错误");
            log.info("密码错误");
        }
        return result;
    }

    /**
     * shiro会话管理
     * SessionManager（会话管理）：管理所有Subject的session，包括创建、维护、删除、失效、验证登工作。SessionManager是顶层组件，由SecurityManager控制
     * SessionManager提供了三个默认实现
     * 1.DefaultSessionManager：用于JavaSE环境
     * 2.ServiceContainerSessionManager：用于web环境，直接使用Servlet容器的会话
     * 3.DefaultWebSessionManager：用于web环境，自己维护的会话（自己维护的会话，直接废弃了Servlet容器的会话管理）
     * 注：在web程序中，通过shiro的Subject.login()方法登录成功后，用户的认证信息实际上是保存在HttpSession中
     *
     * @param session
     * @return String
     */
    @PostMapping(value = "showSession")
    public String showSession(HttpSession session) {
        //获取session的所有键值
        Enumeration<?> enumeration = session.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            //获取session键值
            String name = enumeration.nextElement().toString();
            //根据键值获取session的值
            Object value = session.getAttribute(name);
            //打印结果
            System.out.println("name: " + name + "  value: " + value);
        }
        return "查看session成功";
    }

    /**
     * 获取用户信息
     *
     * @param name
     * @return com.jh.shiro.entity.Result
     * @author jh
     * @date 2020/3/11
     */
    @PostMapping("shiro/getUserInfo")
    public DataResult getUserInfo(@RequestParam String name) {
        log.info(name);
        DataResult dataResult;
        try {
            dataResult = userService.getUserInfo(name);
        } catch (Exception e) {
            dataResult = new DataResult();
            dataResult.setMessage("查询失败");
            dataResult.setSuccess(false);
            log.error("查询失败");
        }
        return dataResult;
    }

    /**
     * 未授权跳转
     *
     * @param
     * @return java.lang.String
     * @author jh
     * @date 2020/3/11
     */
    @GetMapping(value = {"unAuthorization"})
    public String unAuthorization() {
        return "unAuthorization";
    }

    /**
     * 获取用户权限
     *
     * @param name 用户名
     * @return Result
     * @author jh
     * @date 2020/3/11
     */
    @PostMapping(value = {"shiro/listAuthInfo"})
    public DataResult getAuth(String name) {
        DataResult dataResult;
        try {
            dataResult = userService.getAuth(name);
        } catch (Exception e) {
            dataResult = new DataResult();
            dataResult.setMessage("");
            dataResult.setSuccess(false);
            log.error("查询失败");
        }
        return dataResult;
    }

  /*  @PostMapping(value = {"jwtLogin"})
    public Result jwtLogin(@RequestBody HashMap<String, String> logMap) {
        Result result = null;
        try {
            String name = logMap.get("name");
            String password = logMap.get("password");
            Map<String, Object> map = new HashMap<>();
            map.put("sex", "男");
            UserEntity user = (UserEntity) userService.getUserInfo(name).getData();
            if (!password.equals(*//*user.getPassword())) {
                return new Result(ResultCode.USERNAMEORPASSWORDERROR);
            }*//*
            String token = JwtUtils.createJwtToken(user.getId().toString(), name, map);
            result = new Result(ResultCode.SUCCESS, token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }*/

    @PostMapping(value = {"jwtGetUserInfo"})
    public Result jwtGetUserInfo(HttpServletRequest request) {
        Result result = null;
        try {
            String authorization = request.getHeader("Authorization");
            String token = authorization.replace("Bearer ", "");
            Claims claims = JwtUtils.parseJwtToken(token);
            UserEntity userInfo = userService.getUserInfoById(Integer.parseInt(claims.getId()));
            result = new Result(ResultCode.SUCCESS, userInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    @GetMapping(value = "autherror")
    public String autherror(int code) {
        return code == 1 ? "未登录！" : "未授权！";
    }
}
