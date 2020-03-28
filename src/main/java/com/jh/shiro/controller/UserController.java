package com.jh.shiro.controller;

import com.jh.shiro.entity.DataResult;
import com.jh.shiro.entity.UserEntity;
import com.jh.shiro.service.UserService;
import com.jh.shiro.util.Result;
import com.jh.shiro.util.ResultCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用户Controller
 * Thymeleaf只能用@Controller，切记
 *
 * @author: jh
 * @date: 2020/3/10
 */
@Controller
@CrossOrigin
public class UserController {
    @Resource(name = "userServiceImpl")
    private UserService userService;

    private static final Log LOG = LogFactory.getLog(UserController.class);

    /**
     * 获取用户信息
     *
     * @param
     * @return com.jh.shiro.entity.Result
     * @author jh
     * @date 2020/3/10
     */
    @PostMapping(value = {"shiro/listUserInfo"})
    @ResponseBody
    public DataResult listUserInfo() {
        DataResult dataResult;
        try {
            dataResult = userService.listUserInfo();
        } catch (Exception e) {
            dataResult = new DataResult();
            dataResult.setMessage("查询失败");
            dataResult.setSuccess(false);
            LOG.error(e.getMessage());
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
    @ResponseBody
    public Result login(@RequestBody UserEntity userEntity, Model model) {
        Result result = null;
        StringBuilder sb = new StringBuilder("用户名: ");
        String name = userEntity.getName();
        String password = userEntity.getPassword();
        LOG.info(sb.append(name).append("  密码:").append(password));
        /**
         * 使用shiro编写认证操心
         */
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);

        //3.执行登录方法
        try {
            subject.login(token);
            model.addAttribute("msg", "登录成功");
            LOG.info("登录成功");
            result = new Result(ResultCode.LOGINSUCCESS);
            result.setData(userEntity);
        } catch (UnknownAccountException e) {
            result = new Result(ResultCode.FAIL);
            result.setMessage("用户名不存在");
            LOG.info("用户名不存在");
        } catch (IncorrectCredentialsException e) {
            result = new Result(ResultCode.FAIL);
            result.setMessage("密码错误");
            LOG.info("密码错误");
        }
        return result;
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
    @ResponseBody
    public DataResult getUserInfo(@RequestParam String name) {
        LOG.info(name);
        DataResult dataResult;
        try {
            dataResult = userService.getUserInfo(name);
        } catch (Exception e) {
            dataResult = new DataResult();
            dataResult.setMessage("查询失败");
            dataResult.setSuccess(false);
            LOG.fatal("查询失败");
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
     * @param id 用户id
     * @return Result
     * @author jh
     * @date 2020/3/11
     */
    @PostMapping(value = {"shiro/getAuthInfo"})
    @ResponseBody
    public DataResult getAuth(Integer id) {
        DataResult dataResult;
        try {
            dataResult = userService.getAuth(id);
        } catch (Exception e) {
            dataResult = new DataResult();
            dataResult.setMessage("");
            dataResult.setSuccess(false);
            LOG.fatal("查询失败");
        }
        return dataResult;
    }
}
