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
 */
@Component // 所有filter都在intercepter之前执行
@Order(4)
public class AclInterceptor extends HandlerInterceptorAdapter {

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
