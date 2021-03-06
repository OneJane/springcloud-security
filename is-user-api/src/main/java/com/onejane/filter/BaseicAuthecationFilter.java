package com.onejane.filter;

import com.lambdaworks.crypto.SCryptUtil;
import com.onejane.user.User;
import com.onejane.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 身份认证 支持两种认证方式basic session
 */
@Component
@Order(2)
public class BaseicAuthecationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;


    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(2);
        String authHeader = request.getHeader("Authorization");
        if(StringUtils.isNotBlank(authHeader)){
            String token64 = StringUtils.substringAfter(authHeader,"Basic ");
            // 解析获取明文
            String token = new String(Base64Utils.decodeFromString(token64));
            String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(token,":");
            String username = items[0];
            String password = items[1];
            User user = userRepository.findByUsername(username);
            if(user!=null && SCryptUtil.check(password,user.getPassword())){
                // 支持http basic 访问
                request.getSession().setAttribute("user",user);
                request.getSession().setAttribute("temp","yes");
            }
        }

        //解决登陆和httpBasic访问
        try{
            filterChain.doFilter(request,response);
        }finally {
            // 支持登录方式访 问
            HttpSession session = request.getSession();
            if(session.getAttribute("temp")!=null){
                session.invalidate();
            }
        }

    }


}
