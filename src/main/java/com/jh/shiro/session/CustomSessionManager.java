/**
 * @title :  CustomSessionManager.java
 * @author: jh
 * @create: 2020/5/11 22:20
 * @modify:
 * @description : 自定义SessionManager
 * @version: 1.0
 */
package com.jh.shiro.session;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @description：自定义SessionManager
 * @author: jh
 * @version: 1.0
 * @date: 2020/5/11 22:21
 */
public class CustomSessionManager extends DefaultWebSessionManager {
    /**
     * 指定sessionId的获取方式
     * 头信息中具有sessionId
     *      请求头：Authorization：sessionId
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response){
        String id = WebUtils.toHttp(request).getHeader("Authorization");
        if (StringUtils.isEmpty(id)){
            //如果没有携带，生成新的sessionId
            return super.getSessionId(request,response);
        } else {
            //返回sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        }
    }
}
