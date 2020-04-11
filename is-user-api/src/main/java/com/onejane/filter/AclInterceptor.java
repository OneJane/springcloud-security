package com.onejane.filter;

import com.onejane.user.User;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问控制
 * 401 请求需要身份认证 无身份认证信息，身份认证信息有误，可在请求头添加身份证明避免401
 * 403 身份认证成功， 请求需要权限，请求做任何修改403都不会过去，除非被授权
 */
@Component // 所有filter都在intercepter之前执行
@Order(4)
public class AclInterceptor extends HandlerInterceptorAdapter {

    // 过滤登录接口
    private String[] permitUrls = new String[]{"/users/login"};

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(4);
        boolean result = true;
        System.out.println("URL--->"+request.getRequestURI());
        if(!ArrayUtils.contains(permitUrls,request.getRequestURI())){
            User user = (User) request.getSession().getAttribute("user");
            // 所有请求都需要身份认证 postman Authorization Basic Auth username:yx2 password:123456
            if(user == null){
                response.setContentType("text/plain");
                response.getWriter().write("need authentication");
                response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
                result = false;
            }else{
                String method = request.getMethod();
                if(!user.hasPermission(method)){
                    response.setContentType("text/plain");
                    response.getWriter().write("forbidden");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    result = false;

                }
            }
        }
        return result;
    }
}
